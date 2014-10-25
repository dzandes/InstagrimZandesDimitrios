package uk.ac.dundee.computing.aec.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.Bytes;
import com.jhlabs.composite.ColorDodgeComposite;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.ImageUtils;
import com.jhlabs.image.InvertFilter;
import com.jhlabs.image.PointFilter;

import java.awt.CompositeContext;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import static org.imgscalr.Scalr.*;

import org.imgscalr.Scalr.Method;

import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

public class PicModel {

    Cluster cluster;

    public void PicModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void insertPic(byte[] b, String type, String name, String user) {
    	
        try {
        	
            Convertors convertor = new Convertors();

            String types[]=Convertors.SplitFiletype(type);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int length = b.length;
            java.util.UUID picid = convertor.getTimeUUID();
            
            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            Boolean success = (new File("/var/tmp/instagrim/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/instagrim/" + picid));

            output.write(b);
            byte [] thumbb = picresize(picid.toString(),types[1]);
            int thumblength= thumbb.length;
            ByteBuffer thumbbuf=ByteBuffer.wrap(thumbb);
            byte[] processedb = picdecolour(picid.toString(),types[1]);
            ByteBuffer processedbuf=ByteBuffer.wrap(processedb);
            int processedlength=processedb.length;
            Session session = cluster.connect("instagrim");

            PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image,thumb,processed, user, interaction_time,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);

            Date DateAdded = new Date();
            session.execute(bsInsertPic.bind(picid, buffer, thumbbuf,processedbuf, user, DateAdded, length,thumblength,processedlength, type, name));
            session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
            session.close();

        } catch (IOException ex) {
        	
            System.out.println("Error --> " + ex);
            
        }
    }
    
    
    // Methods added (deletePic, insertTitle, retrieveTitle, getSketchPic)
    public void deletePic(java.util.UUID photoID){
    	
    	Session session = CassandraHosts.getCluster().connect("instagrim");
    	
    	PreparedStatement ps = session.prepare("delete from pics where picid=?");
   	    BoundStatement boundStatement = new BoundStatement(ps);
   	    session.execute(boundStatement.bind(photoID));
   	    
   	    PreparedStatement ps2 = session.prepare("delete from userpiclist where picid=?");
	    BoundStatement boundStatement2 = new BoundStatement(ps2);
	    session.execute(boundStatement2.bind(photoID));
    	
	    session.close();
	    
    }
    
    public void insertTitle(String heading, String userName){
    	
    	Session session = CassandraHosts.getCluster().connect("instagrim");
    	
    	PreparedStatement ps2 = session.prepare("select picid,title from pics");
    	ResultSet rs = null;
    	BoundStatement boundStatement2 = new BoundStatement(ps2);
    	rs = session.execute(boundStatement2.bind());
    	
    	java.util.UUID photoUUID = null;
    	
    	for(Row row : rs){
    		
    		java.util.UUID tempID = row.getUUID("picid");
    		String temp = row.getString("title");
    		if(temp==null){
    			
    			photoUUID = row.getUUID("picid");
    			
    		}
    		
    	} 
        
        PreparedStatement ps = session.prepare("update pics SET title=? where picid=?");
   	    BoundStatement boundStatement = new BoundStatement(ps);
   	    session.execute(boundStatement.bind(heading, photoUUID));
   	    
   	    session.close();
    	
    }
    
    public String retrieveTitle(java.util.UUID photoid){
    	
    	Session session = CassandraHosts.getCluster().connect("instagrim");
    	
    	PreparedStatement ps = session.prepare("select title from pics where picid=?");
    	ResultSet rs = null;
    	BoundStatement boundStatement = new BoundStatement(ps);
    	rs = session.execute(boundStatement.bind(photoid));
    	
    	String titleSearching = "";
    	
    	for(Row row : rs){
    		
    		titleSearching = row.getString("title");
    		
    	}
    	
    	session.close();
    	
    	return titleSearching;
    	
    }

    public byte[] picresize(String picid,String type) {
    	
        try {
        	
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            BufferedImage thumbnail = createThumbnail(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
            
        } catch (IOException et) {

        }
        
        return null;
        
    }
    
    public byte[] picdecolour(String picid,String type) {
    	
        try {
        	
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            BufferedImage processed = createProcessed(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
            
        } catch (IOException et) {

        }
        
        return null;
        
    }

    public static BufferedImage createThumbnail(BufferedImage img) {
    	
        img = resize(img, Method.SPEED, 250, OP_ANTIALIAS, OP_GRAYSCALE);
        // Let's add a little border before we return result.
        return pad(img, 2);
        
    }
    
   public static BufferedImage createProcessed(BufferedImage img) {
	   
        int Width=img.getWidth()-1;
        img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_GRAYSCALE);
        return pad(img, 4);
        
    }
   
    public java.util.LinkedList<Pic> getPicsForUser(String User) {
    	
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = CassandraHosts.getCluster().connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
        	
            System.out.println("No Images returned");
            return null;
            
        } else {
        	
            for (Row row : rs) {
                
            	Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                Pics.add(pic);

            }
        }
        return Pics;
    }
    
    
    // ADD for sketch filter (using JHLabs open source library)
    public Pic getSketchPic(int image_type, java.util.UUID picid){
    	
    	Session session = cluster.connect("instagrim");
    	ByteBuffer bImage = null;
    	String type = null;
    	int length = 0;
    	
    	try{
    		
    		Convertors con = new Convertors();
    		ResultSet rs = null;
    		PreparedStatement ps = null;
    		
    		if(image_type == Convertors.DISPLAY_IMAGE){
    			
    			ps = session.prepare("select image,imagelength,type from pics where picid=?");
    			
    		}
    		
    		BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(picid));
            
            if (rs.isExhausted()) {
            	
                System.out.println("No Images returned");
                return null;
                
            }else{
            	
                for(Row row : rs) {
                	
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                    	
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                        
                    }
                    
                    type = row.getString("type");
                 }	
    	    }
            
    	}catch(Exception e){
    		
    		 System.out.println("Can't get Pic" + e);
             return null;
    		
    	}
    	
    	session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);
        
        byte[] tempArr = null;
        tempArr = p.getBytes();
        
        BufferedImage buff = Convertors.byteArrayToBufferedImage(tempArr);
        buff = ImageUtils.convertImageToARGB(buff);
        
        // First Grayscale
        PointFilter grayScaleFilter = new GrayscaleFilter();
        BufferedImage grayScale = new BufferedImage(buff.getWidth(), buff.getHeight(), buff.getType());
        grayScaleFilter.filter(buff, grayScale);
        
        // Then Inverted Grayscale
        BufferedImage inverted = new BufferedImage(buff.getWidth(), buff.getHeight(), buff.getType());
        PointFilter invertFilter = new InvertFilter();
        invertFilter.filter(grayScale,inverted);
        
        // Then Gaussian Blur
        GaussianFilter gaussianFilter = new GaussianFilter(20);
        BufferedImage gaussianFiltered = new BufferedImage(buff.getWidth(), buff.getHeight(), buff.getType());
        gaussianFilter.filter(inverted, gaussianFiltered);
        
        // Then color dodge
        ColorDodgeComposite cdc = new ColorDodgeComposite(1.0f);
        CompositeContext cc = cdc.createContext(inverted.getColorModel(), grayScale.getColorModel(), null);
        Raster invertedR = gaussianFiltered.getRaster();
        Raster grayScaleR = grayScale.getRaster();
        BufferedImage composite = new BufferedImage(buff.getWidth(), buff.getHeight(), buff.getType());
        WritableRaster colorDodgedR = composite.getRaster();
        cc.compose(invertedR, grayScaleR , colorDodgedR);
        
        Pic temp = new Pic();
        String[] ar = Convertors.SplitFiletype(type);
        byte[] imageAr = Convertors.bufferedImageToByteArray(composite, ar[1]);
        ByteBuffer buf = ByteBuffer.wrap(imageAr);
        temp.setPic(buf, length, type);
        
        System.out.println("Filter Successfull");
        
        return temp;
        
    }

    public Pic getPic(int image_type, java.util.UUID picid) {
    	
        Session session = cluster.connect("instagrim");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        
        try {
        	
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;
         
            if (image_type == Convertors.DISPLAY_IMAGE) {
                
                ps = session.prepare("select image,imagelength,type from pics where picid =?");
                
            } else if (image_type == Convertors.DISPLAY_THUMB) {
            	
                ps = session.prepare("select thumb,imagelength,thumblength,type from pics where picid =?");
                
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
            	
                ps = session.prepare("select processed,processedlength,type from pics where picid =?");
                
            }
            
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
            	
                System.out.println("No Images returned");
                return null;
                
            } else {
            	
                for(Row row : rs) {
                	
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                    	
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                        
                    } else if (image_type == Convertors.DISPLAY_THUMB) {
                    	
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");
                
                    } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                    	
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                        
                    }
                    
                    type = row.getString("type");

                }
            }
            
        } catch(Exception et) {
        	
            System.out.println("Can't get Pic" + et);
            return null;
            
        }
        
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;

    }

}