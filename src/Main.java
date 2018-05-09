
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;



/**
 * Aplicaciones Telemáticas para la Administración
 * 
 * Este programa debe ller el nombre y NIF de un usuario del DNIe, formar el identificador de usuario y autenticarse con un servidor remoto a través de HTTP 
 * @author Juan Carlos Cuevas Martínez
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        ByteArrayInputStream bais=null;
        
        //TAREA 2. Conseguir que el método LeerNIF de ObtenerDatos devuelva el 
        //         correctamente los datos de usuario 
        ObtenerDatos od = new ObtenerDatos();
        Usuario user = od.LeerNIF();
        if(user!=null) {
        	String usuario = user.getPrimeraLetraNombre()+user.getApellido1()+user.getPrimeraLetra2Apellido();
        	 String clave = user.getNif();
      
           // System.out.println("usuario: "+user.toString());
        //System.out.println("usuariodni: " +user.usuario_dni);
        
        //TAREA 3. AUTENTICAR EL CLIENTE CON EL SERVIDOR

        URL url = new URL("http://localhost:8080/dni/Login");
       Map<String, Object> params = new LinkedHashMap<>();
    
       params.put("usuario", usuario);
       params.put("clave", clave);
       
       StringBuilder post = new StringBuilder();
       for( Map.Entry<String, Object> param : params.entrySet()) {
    	   if(post.length() !=0 )
    		   post.append('&');
           post.append(URLEncoder.encode(param.getKey(), "UTF-8"));
           post.append('=');
           post.append(URLEncoder.encode(String.valueOf(param.getValue()),
                   "UTF-8"));
       }
       byte[] postBytes = post.toString().getBytes("UTF-8");
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       conn.setRequestMethod("POST");
       conn.setRequestProperty("Content-Type",
               "application/x-www-form-urlencoded");
       conn.setRequestProperty("Content-Length",
               String.valueOf(postBytes.length));
       conn.setDoOutput(true);
       conn.getOutputStream().write(postBytes);
       Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
       for (int c = in.read(); c != -1; c = in.read())
           System.out.print((char) c);
        }
    }
}
