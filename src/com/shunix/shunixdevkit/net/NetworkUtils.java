package com.shunix.shunixdevkit.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

/**
 * 
 * @author Ray WANG
 * @since Nov 27th, 2013
 * @version 1.0.0
 */
public class NetworkUtils {

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	public static String getIspIpAddress() {
		String ipAddress = null;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface
						.getNetworkInterfaces(); en.hasMoreElements();) {
					NetworkInterface networkInterface = en.nextElement();
					for (Enumeration<InetAddress> ipAddr = networkInterface
							.getInetAddresses(); ipAddr.hasMoreElements();) {
						InetAddress inetAddress = ipAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()) {
							ipAddress = inetAddress.getHostAddress();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				for (Enumeration<NetworkInterface> en = NetworkInterface
						.getNetworkInterfaces(); en.hasMoreElements();) {
					NetworkInterface networkInterface = en.nextElement();
					for (Enumeration<InetAddress> enumIpAddr = networkInterface
							.getInetAddresses(); enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();
						if (!inetAddress.isLoopbackAddress()
								&& InetAddressUtils.isIPv4Address(inetAddress
										.getHostAddress())) {
							ipAddress = inetAddress.getHostAddress().toString();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ipAddress;
	}

	public static String getWifiIpAddress(Context context) {
		String ipAddress = null;
		try {
			WifiManager wifiManger = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManger.getConnectionInfo();
			int addrNum = wifiInfo.getIpAddress();
			ipAddress = (addrNum & 0xFF) + "." + ((addrNum >> 8) & 0xFF) + "."
					+ ((addrNum >> 16) & 0xFF) + (addrNum >> 24 & 0xFF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipAddress;
	}
}
