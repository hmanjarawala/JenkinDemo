package com.automationanywhere.controlroomapi.util;

import java.io.IOException;
import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try{
            String token = RestApiUtil.getInstance().getAccessToken("https://aa-se-ind-5.my.automationanywhere.digital",
                    "himanshum", "Admin@12345");
            System.out.println("Token: " + token);
        }catch (MalformedURLException meu){
            meu.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
