/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package products;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;

/**
 * REST Web Service
 *
 * @author c0653602
 */
@Path("generic")
public class GenericResource {
    DBConnection con=new DBConnection();
    Connection conn=null;
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of ProductResource
     */
    public GenericResource() {
       conn=con.getConnection();
    }

    /**
     * Retrieves representation of an instance of com.oracle.products.ProductResource
     * @return an instance of java.lang.String
     */
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public String getAllProducts() throws SQLException
   {
       if(conn==null)
       {
           return "not connected";
       }
       else {
           String query="Select * from products";
           PreparedStatement pstmt = conn.prepareStatement(query);
           ResultSet rs = pstmt.executeQuery();
           String result="";
           JSONArray productArr = new JSONArray();
           while (rs.next()) {
                Map productMap = new LinkedHashMap();
                productMap.put("productID", rs.getInt("product_id"));
                productMap.put("name", rs.getString("name"));
                productMap.put("description", rs.getString("description"));
                productMap.put("quantity", rs.getInt("quantity"));
                productArr.add(productMap);
           }
            result = productArr.toString();
          return  result.replace("},", "},\n");
        }
       
   }
   
   @GET
   @Path("{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public String getproduct(@PathParam("id") int id) throws SQLException {
   
      if(conn==null)
       {
           return "not connected";
       }
       else {
           String query="Select * from products where product_id = ?";
           PreparedStatement pstmt = conn.prepareStatement(query);
           pstmt.setInt(1,id);
           ResultSet rs = pstmt.executeQuery();
           String result="";
           JSONArray productArr = new JSONArray();
           while (rs.next()) {
                 Map productMap = new LinkedHashMap();
                productMap.put("productID", rs.getInt("product_id"));
                productMap.put("name", rs.getString("name"));
                productMap.put("description", rs.getString("description"));
                productMap.put("quantity", rs.getInt("quantity"));
                productArr.add(productMap);
           }    
                result = productArr.toString();
                
                 return result;
      }
   
   }
   
   @POST
   @Path("/products")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.TEXT_PLAIN)
   public String postProduct(String str) throws SQLException{
       JsonParser parser= Json.createParser(new StringReader(str));
       Map<String,String> productMap = new LinkedHashMap<String,String>();
       String key="";
       String val="";
       
       while(parser.hasNext()){
        JsonParser.Event event=parser.next();
            switch (event){
            case KEY_NAME :
              key = parser.getString();
              break;
            case VALUE_STRING:
              val=parser.getString();
              productMap.put(key, val);
              break;
            case VALUE_NUMBER:     
              val=parser.getString();
              productMap.put(key, val);
              break;  
            default :
              break;  
            }
       }    
       if(conn == null){
           return "Not connected";
       }
       else {
            String query="INSERT INTO products (name,decription,quantity) VALUES (?,?,?)";
            PreparedStatement pstmt=conn.prepareStatement(query);
            pstmt.setNString(1, productMap.get("product_id"));
            pstmt.setNString(2, productMap.get("name"));
            pstmt.setNString(3, productMap.get("description"));
            pstmt.setNString(4, productMap.get("quantity"));
            return "row has been inserted into the database";
           }
       
       
   }
   
   
   @PUT
   @Path("{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   public String  putProduct(@PathParam("id")  int id,String str) throws SQLException{
    JsonParser parser= Json.createParser(new StringReader(str));
       Map<String,String> productMap = new LinkedHashMap<String,String>();
       String key="";
       String val="";
       
       while(parser.hasNext()){
        JsonParser.Event event=parser.next();
            switch (event){
            case KEY_NAME :
              key = parser.getString();
              break;
            case VALUE_STRING:
              val=parser.getString();
              productMap.put(key, val);
              break;
            case VALUE_NUMBER:     
              val=parser.getString();
              productMap.put(key, val);
              break;  
            default :
              break;  
            }
       }    
       if(conn == null){
           return "Not connected";
       }
       else {
            String query="UPDATE products SET  name = ?, SET decription = ?, SET quantity = ? WHERE id = ? ";
            PreparedStatement pstmt=conn.prepareStatement(query);
            pstmt.setInt(4, id);
            pstmt.setNString(1, productMap.get("name"));
            pstmt.setNString(2, productMap.get("description"));
            pstmt.setNString(3, productMap.get("quantity"));
            return "row has been updated into the database";
           }
   
   }
 
   @DELETE
   @Path("{id}")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.TEXT_PLAIN)
   public String deleteProduct(@PathParam("id") int id) throws SQLException{
       
        if(conn==null)
        {
           return "not connected";
        }
        else {
           String query="DELETE FROM products WHERE product_id = ?";
           PreparedStatement pstmt = conn.prepareStatement(query);
           pstmt.setInt(1,id);
           pstmt.executeUpdate();
           return "The specified row is deleted";
           
        }
 
   }
   }