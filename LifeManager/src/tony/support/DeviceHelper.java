package tony.support;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;

import com.lifemanager.logging.Logger;

public class DeviceHelper {
	private static final Logger LOG = Logger.getLogger(DeviceHelper.class);

	private static DisplayMetrics sDisplayMetrics = null;

	public static void loadScreenInfo(Activity activity) {
		if (sDisplayMetrics == null) {
			sDisplayMetrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay()
					.getMetrics(sDisplayMetrics);
			LOG.info(
					"ScreenInfo [width={} height={} density={} densityDpi={} xdpi={} ydpi={} scaledDensity={}]",
					new Object[] { sDisplayMetrics.widthPixels,
							sDisplayMetrics.heightPixels,
							sDisplayMetrics.density,
							sDisplayMetrics.densityDpi, sDisplayMetrics.xdpi,
							sDisplayMetrics.ydpi, sDisplayMetrics.scaledDensity });
		}
	}

	public static int getScreenWidth() {
		return sDisplayMetrics.widthPixels;
	}

	public static int getScreenHeight() {
		return sDisplayMetrics.heightPixels;
	}

	public static float getDensity() {
		return sDisplayMetrics.density;
	}

	public static boolean isScreenLocked(Context context) {
		return ((KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE))
				.inKeyguardRestrictedInputMode();
	}

	public static DisplayMetrics getDisplayMetrics() {
		return sDisplayMetrics;
	}

	public static boolean isWifiOk(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		return wm != null && wm.isWifiEnabled();
	}

	public static String getCpuInfo() {
		String cpuInfo = null;

		String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
		cpuInfo = shellExec(args);
		if (cpuInfo != null) {
			cpuInfo = cpuInfo.replaceAll(" ", "");
			cpuInfo = cpuInfo.replaceAll("\t", "");
			cpuInfo = cpuInfo.replaceAll("\n", " ");
		}

		return cpuInfo;
	}

	private static String shellExec(String[] args) {
		String result = null;

		ProcessBuilder processBuilder = new ProcessBuilder(args);
		Process process = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			process = processBuilder.start();
			baos = new ByteArrayOutputStream();
			is = process.getInputStream();
			int read = -1;
			while (-1 != (read = is.read())) {
				baos.write(read);
			}
			result = new String(baos.toByteArray());
		} catch (Exception e) {
			LOG.warn("shell exec failed. err={}", e.getMessage());

			result = null;
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				LOG.warn("shell exec close failed. err={}", e.getMessage());
			}
		}

		return result;
	}

}
