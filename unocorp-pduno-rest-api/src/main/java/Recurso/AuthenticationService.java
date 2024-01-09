package Recurso;

import java.io.Serializable;
import java.util.Base64;
import java.util.StringTokenizer;

public class AuthenticationService implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean authenticate(String authCredentials) {
        if (null == authCredentials) {
            return false;
        }

        final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (Exception ex) {
            System.out.println("CLASE: " + this.getClass().getName() + " METODO: authenticate ERROR: " + ex.toString());
        }

        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        boolean authenticationStatus = false;
        try {
            if (username.equals("TransportesGPS") && password.equals("c45eCR4eH5n8")) {
                authenticationStatus = true;
            }
        } catch (Exception ex) {
            System.out.println("CLASE: " + this.getClass().getName() + " METODO: authenticate ERROR: " + ex.toString());
        }

        return authenticationStatus;
    }

}
