package cl.nombrempresa.appfun.clases;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

/*
 Creado 2016  Jonathan Andrés Vásquez Subiabre
 */


public class ObtenerIp {

    // funcion que obtiene puerta de enlace *********************
    public String ObtenerIp(Context context) {
        // declaracion de variables
        DhcpInfo dhcpInfo;
        String puertaEnlace;

        // obtener puerta de Enlace
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        dhcpInfo = wifiManager.getDhcpInfo();
        puertaEnlace = Formatter.formatIpAddress(dhcpInfo.gateway);

        return puertaEnlace;// retornar puerta de enlace
    }// fin funcion
}
