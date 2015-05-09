package com.gabriel.eshop.custom_view_framework;


import com.gabriel.eshop.entity.Product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.gabriel.eshop.controller.SessionAttributes.PRODUCTS_IN_BUCKET;

//todo: realize old session clearing functionality
//todo: realize session listeners functionality
//todo: realize storage session in cookies
//todo: rewrite product bucket to this session implementation ( Base64 or ""session"->{"beer"->3, "fish"->1} )
public class CustomHttpSessionOnClientRepository {

    public static CustomHttpSession getSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie client = null;
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(PRODUCTS_IN_BUCKET.equals(cookie.getName())) {
                    client = cookie;
                    break;
                }
            }
        }
        CustomHttpSession result = new CustomHttpSession();
        if(client != null) {
            try {
                String value = client.getValue();
                System.out.println(" << " + value);
                if(value != null) {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(value)));
                    Map<Product, Integer> readObject = (Map<Product, Integer>) ois.readObject();
                    result.setAttribute(PRODUCTS_IN_BUCKET, readObject);
                    System.out.println(" <---- " + readObject);
                    return result;
                }
            } catch (IOException | ClassNotFoundException e) {
                /*NOP*/
            }
        }
        result.setAttribute(PRODUCTS_IN_BUCKET, new ConcurrentHashMap<Product, Integer>());
        return result;
    }

    public static void saveSession(HttpServletResponse response, CustomHttpSession session) {
        Object value = session.getAttribute(PRODUCTS_IN_BUCKET);
        if(value != null) {
            System.out.println(" ----> " + value);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(value);
                String string = Base64.getEncoder().encodeToString(baos.toByteArray());
                System.out.println(" >> " + string);
                response.addCookie(new Cookie(PRODUCTS_IN_BUCKET, string));
            } catch (IOException e) {
                /*NOP*/
            }

        }
    }

}
