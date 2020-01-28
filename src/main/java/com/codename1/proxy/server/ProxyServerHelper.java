/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */

package com.codename1.proxy.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shai Almog
 */
public class ProxyServerHelper {
    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_VOID = 0;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BYTE = 1;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_CHAR = 2;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_SHORT = 3;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_INT = 4;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_LONG = 5;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_DOUBLE = 6;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_FLOAT = 7;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BOOLEAN = 8;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BYTE_OBJECT = 9; 

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_CHARACTER_OBJECT = 10;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_SHORT_OBJECT = 11;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_INTEGER_OBJECT = 12;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_LONG_OBJECT = 13;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_DOUBLE_OBJECT = 14;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_FLOAT_OBJECT = 15;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BOOLEAN_OBJECT = 16;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_STRING = 17;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BYTE_ARRAY = 18;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_CHAR_ARRAY = 19;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_SHORT_ARRAY = 20;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_INT_ARRAY = 21;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_LONG_ARRAY = 22;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_DOUBLE_ARRAY = 23;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_FLOAT_ARRAY = 24;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_BOOLEAN_ARRAY = 25;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_STRING_ARRAY = 26;

    /**
     * Web protocol argument/return type
     */
    public static final int TYPE_EXTERNALIABLE = 1000;

    public static WSDefinition createServiceDefinition(String name, int returnType, int... argumentTypes) {
        WSDefinition wd = new WSDefinition();
        wd.arguments = argumentTypes;
        wd.returnType = returnType;
        wd.name = name;
        return wd;
    }
    
    /**
     * Parses the arguments to a method 
     * @param dis the stream from the client
     * @param def the method definition
     * @return arguments for the method
     */
    public static String[] readMethodArguments(DataInputStream dis, WSDefinition def) throws IOException {
        String[] result = new String[def.arguments.length];
        for(int iter = 0 ; iter < result.length ; iter++) {
            switch(def.arguments[iter]) {
                // process request parameters
                case TYPE_STRING:
                    if(dis.readBoolean()) {
                        result[iter] = dis.readUTF();
                    }
                    break;                               
                default:
                    throw new RuntimeException("Unrecognized type: " + def.arguments[iter]);
            }
        }
        return result;
    }
     
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        OutputStream o = resp.getOutputStream();
        o.write(0);
        o.close();
    }
    
    /**
     * Returns an OK response to the client
     * @param resp the response object
     * @param def the method definition
     * @param response the result from the method
     */
    public static void writeResponse(HttpServletResponse resp, WSDefinition def, String response) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        DataOutputStream dos = new DataOutputStream(resp.getOutputStream());
        if(response != null) {
            dos.writeBoolean(true);
            dos.writeUTF(response);
        } else {
            dos.writeBoolean(false);
        }
        dos.close();
    }       
    
    /**
     * Webservice definition type, allows defining the argument values for a specific WS call
     */
    public static class WSDefinition {
        String name;
        int returnType;
        int[] arguments;
    }
}
