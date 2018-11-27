package com.client.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;

import com.client.socket.dto.SocketAutorizacaoDto;
import com.client.socket.dto.SocketEntradaDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SocketClient {

	public static void main(String[] args) throws Exception {
        int portNumber = 33442;
        Socket socket = new Socket(InetAddress.getLocalHost(), portNumber);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        SocketEntradaDto dto = new SocketEntradaDto();
        dto.setAction("withdraw");
        dto.setCardnumber("123456");
        dto.setAmount(BigDecimal.TEN);
        
        Gson gson = new GsonBuilder().create();

        oos.writeObject(gson.toJson(dto));

        String retorno = "";
        while((retorno = (String) ois.readObject()) != null) {
        	SocketAutorizacaoDto socketAutorizacaoDto = converterJsonToDto(retorno);
            System.out.println(socketAutorizacaoDto);
            break;
        }

        ois.close();
        oos.close();
        socket.close();
    }

	private static SocketAutorizacaoDto converterJsonToDto(String retorno) {
		Gson obj = new GsonBuilder().create();
		SocketAutorizacaoDto socketAutorizacaoDto = obj.fromJson(retorno, SocketAutorizacaoDto.class);
		return socketAutorizacaoDto;
	}
}
