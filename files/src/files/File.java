/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * @author jairm
 */
public class File {
    private String url="D:\\archivos\\files\\src\\files\\prueba.txt";
    private DataOutputStream write;
    private DataInputStream read;
    
    public File(){
        read = null;
        write=null;
    }
    
    public Contacts FileSearch(Contacts cts) throws IOException{
    try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String name="",address="";
        while (true){
            id=read.readInt();
            name=read.readUTF();
            address=read.readUTF();
            if (id==cts.getId()){
                cts.setId(id);
                cts.setName(name);
                cts.setAddress(address);
                return cts;
            }
        }
    }catch(FileNotFoundException ex) {
    System.out.println("error");
    }
    return cts;
    }
    
    public static void copyFileUsingBufferedStream(File source, File dest) throws IOException {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
        inputStream = new FileInputStream("D:\\archivos\\files\\src\\files\\temp.txt");
        outputStream = new FileOutputStream("D:\\archivos\\files\\src\\files\\prueba.txt");
    
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
    } finally {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Manejo de excepción si se produce un error al cerrar el flujo de entrada
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                // Manejo de excepción si se produce un error al cerrar el flujo de salida
            }
        }
    }
}
    
    public void Delete(Contacts cts) throws IOException{
         File tempFile = new File();
        try{
        write = new DataOutputStream(new FileOutputStream("D:\\archivos\\files\\src\\files\\temp.txt", false));
        read = new DataInputStream( new FileInputStream(url));
        while(read.available() > 0){
            int id=read.readInt();
            String name=read.readUTF();
            String address=read.readUTF();
            if(id != cts.getId()){
                write.writeInt(id);
                write.writeUTF(name);
                write.writeUTF(address);
            }
        }
        }finally {
            read.close();
            write.close();
        } 
        
        try {
            copyFileUsingBufferedStream(this,tempFile);
        } catch (IOException e){ 
            System.out.println("jdfsk<<");
        }
    }
    
    public void Edit(Contacts cts) throws IOException{
        
        File tempFile = new File();
        try{
        write = new DataOutputStream(new FileOutputStream("D:\\archivos\\files\\src\\files\\temp.txt", false));
        read = new DataInputStream( new FileInputStream(url));
        while(read.available() > 0){
            int id=read.readInt();
            String name=read.readUTF();
            String address=read.readUTF();
            if(id == cts.getId()){
                write.writeInt(cts.getId());
                write.writeUTF(cts.getName());
                write.writeUTF(cts.getAddress());
            }
            else{
                write.writeInt(id);
                write.writeUTF(name);
                write.writeUTF(address);
            }
        }
        }finally {
            read.close();
            write.close();
        } 
        try {
            copyFileUsingBufferedStream(this,tempFile);
        } catch (IOException e) {  
        } 
    }
    
    public int getMaxId() throws IOException {
    int max = 0;
        try (DataInputStream read = new DataInputStream(new FileInputStream(url))) {
            while (read.available() > 0) {
                int id = read.readInt();
                read.readUTF();
                read.readUTF();
                if (id > max) {
                    max = id;
                    }
                }
        }
        return max;
    }
    
    public void FileWrite(Contacts cts) throws IOException {
    try {
        write = new DataOutputStream(new FileOutputStream(url, true));
        write.writeInt(cts.getId());
        write.writeUTF(cts.getName());
        write.writeUTF(cts.getAddress());
        write.close();
        } catch (FileNotFoundException ex) {
            
        }
    }

}
