import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
public class PasswordControl {
	
	/**
	 * if passwd_file and master_password don't exist in password-manager, the user is not registered yet
	 * then call registerForUser method to register for the user. If the user has already registered, 
	 * call checkMasterPassword method to let the user log in
	 */
    public static void checkRegistration(){
        
        try{
            String pathPassStr = "/Users/ruizhao/Desktop/PasswordManager/passwd_file.txt";
            
            String pathMasterStr = "/Users/ruizhao/Desktop/PasswordManager/master_password.txt";
            
            Path pathPass = Paths.get(pathPassStr);
            
            Path pathMaster = Paths.get(pathMasterStr);
            
            if(Files.notExists(pathPass) || Files.notExists(pathMaster)){
                
                registerForUser();
                
            }else{
                
                checkMasterPassword();
                
            }
        }
        catch(Exception e){
            
            System.out.println(e);
        }
    }

/**
 * create passwwd_file.txt and master_password.txt in password-manager folder.
 * write the user master password into master_password.txt
 */
public static void registerForUser(){

        
        try {
            
             //create a scanner for read command from user
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Enter the master password: ");
            
            String masterPassword = scanner.next();
            
            File filePass = new File("/Users/ruizhao/Desktop/PasswordManager/passwd_file.txt");
            
            File fileMaster = new File("/Users/ruizhao/Desktop/PasswordManager/master_password.txt");
            
            //create the Passwd_file and master_password file
            if (!filePass.exists()){
                
                filePass.createNewFile();
                
            }
            
            if (!fileMaster.exists()){
                
                fileMaster.createNewFile();
                
            }
            
            //write master word into master_password.txt
            
            writeLine("master_password.txt", masterPassword);
            
    }catch(Exception e){
    	
    	e.printStackTrace();
    }
}
/**
 * this method will ask input from user, then if the input master password match with the record
 * in master_password.txt. Call getCommand()
 */

public static void checkMasterPassword(){
    
    //read the master password in the master_password file
    BufferedReader br = null ;
    
    FileReader fr = null ;
    
    try{
        
        //create a scanner for read comman from user
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the master password: ");
        
        String masterPassword = scanner.next();
        
        fr = new FileReader("master_password.txt");
        
        br = new BufferedReader(fr);
        
        String masterRecord = br.readLine();  
        
        if (masterPassword.equals(masterRecord)){
            
            getCommand();
            
        }else{
            
            System.out.println("WRONG MASTER PASSWORD!\n");
            
        }
        
        
    }catch (IOException e){
        
        e.printStackTrace();
        
    }
}
	
	/**
	 * get command from a user.
	 * the expected command would be "check_integrity", "register_account","delete_account"
	 * "change_account", "get_password"
	 * @throws IOException 
	 */
	public static void getCommand() throws IOException {
        //create a scanner for read command from user
        Scanner scanner = new Scanner(System.in);
            
        System.out.println("Enter the command: ");
            
        String masterPassword = scanner.next();
        
        if (masterPassword.equals("check_integrity")){
            
            check_integrity();
            
        }
        if (masterPassword.equals("register_account")){
            
            register_account();
            
        }
        if (masterPassword.equals("delete_account")){
            
            delete_account();
            
        }
        if (masterPassword.equals("change_account")){
            
            change_account();
            
        }
        if (masterPassword.equals("get_password")){
            
            get_password();
            
        }
	}
	/**
	 * this method return false if the passwd_file is not modified without permission
	 * else return true
	 */
   public static boolean check_integrity(){
       
       return true;
   }
   
   /**
    * this method write a single string to a file
    */
   
   public static void writeLine(String filename,String text){
	   
	   BufferedWriter bw = null;
       
       FileWriter fw = null;
       
       try {           
           
           fw = new FileWriter(filename, true);
           
           bw = new BufferedWriter(fw);
           
           bw.write(text + "\n");
    
       }catch(IOException e){
           
           e.printStackTrace();
           
       }finally
	{ 
	   try{
		   
	      if(bw!=null)
	    	  
		 bw.close();
	      
	   }catch(Exception ex){
		   
	       System.out.println("Error in closing the BufferedWriter"+ex);
	       
	    }
	}
   }
   
 
   /**
    * take user name,password,domain name as input, store the encrypted input into passwd_file.txt
    */
   public static void register_account(){
    
       //get username from user
       
       Scanner scanner1 = new Scanner(System.in);
           
       System.out.println("Enter the username: ");
           
       String username = scanner1.next();
       
       String encrypted_username = "";
       
       //get password from user
       
       Scanner scanner2 = new Scanner(System.in);
           
       System.out.println("Enter the password: ");
           
       String password = scanner2.next();
       
       String encrypted_password = "";
       
       //get the domain from the user
       
       Scanner scanner3 = new Scanner(System.in);
           
       System.out.println("Enter the domain: ");
           
       String domain = scanner3.next();
       
       String encrypted_domain = "";
       
       //write information to passwd_file.txt if the account doesn't exist
       
       try{
    	  
       FileReader fr = new FileReader("passwd_file.txt");
       
       BufferedReader br = new BufferedReader(fr);
       
       String text = "";
           
       Boolean userAccountExist = null;
       
       while((text = br.readLine()) != null){
    	   
    	   if (text.equals(domain)){
    		   
    		   if (br.readLine().equals(username)){
                   
                   System.out.println("USER ACCOUNT ALREADY EXIST!\n");
                   
                   text = null;
                   
                   userAccountExist = true;
               }
    	   }else{
               
               userAccountExist = false;
           }
       }
       if (userAccountExist == false){
           
           EncAndDec c_1 = new EncAndDec();
           
            writeLine("passwd_file.txt",c_1.encrypt(domain));
       
            writeLine("passwd_file.txt",c_1.encrypt(username));
       
            writeLine("passwd_file.txt",c_1.encrypt(password));
       }
       
       }catch(Exception e){
    	   
    	   e.printStackTrace();
       }
      
   }
   
   /**
    * take user name,password,domain name as input, delete the encrypted input in passwd_file.txt
 * @throws IOException 
    */
   //delete account
   public static void delete_account() throws IOException{
       
        //get username from user
       
       Scanner scanner1 = new Scanner(System.in);
           
       System.out.println("Enter the username: ");
           
       String username = scanner1.next();
       
       //get password from user
       
       Scanner scanner2 = new Scanner(System.in);
           
       System.out.println("Enter the password: ");
           
       String password = scanner2.next();
       
       //get the domain from the user
       
       Scanner scanner3 = new Scanner(System.in);
           
       System.out.println("Enter the domain: ");
           
       String domain = scanner3.next();
       
       //check if the file exist
      //if it does exist create a new file to replace passwd_file.txt after modification
       
       try{
    	  
       FileReader fr = new FileReader("passwd_file.txt");
       
       BufferedReader br = new BufferedReader(fr);
       
       String text = "";
           
       Boolean userAccountExist = null;
           
       //create a EncAndDec object to decrypt and encrypt the information
       EncAndDec c_2 = new EncAndDec();
       
       while((text = br.readLine()) != null){
    	   
    	   if (c_2.decrypt(text).equals(domain)){
    		   
    		   if (c_2.decrypt(br.readLine()).equals(username)){
                   
                        userAccountExist = true;
                   
                       File temPath = new File("/Users/ruizhao/Desktop/PasswordManager/tem.txt");

                       File deletePath = new File("/Users/ruizhao/Desktop/PasswordManager/passwd_file.txt");

                       if (!temPath.exists()){

                        try{
                            temPath.createNewFile();

                        }catch(Exception e){

                            e.printStackTrace();
                        }

                       }

                       try{

                       FileReader fr2 = new FileReader("passwd_file.txt");

                       BufferedReader br2 = new BufferedReader(fr2);

                       String text2 = "";

                       while((text2 = br2.readLine()) != null){

                           if (!c_2.decrypt(text2).equals(username) && !c_2.decrypt(text2).equals(password) && !c_2.decrypt(text2).equals(domain)){
                               
                               //reencrypt the massage
                               writeLine("tem.txt", c_2.encrypt(c_2.decrypt(text2)));
                           }
                       }


                       }catch(Exception e){

                           e.printStackTrace();
                       }
                       //delete the old passwd_file
                       deletePath.delete();

                       //rename the tem file to passwd_file.txt
                       temPath.renameTo(deletePath);
               }
    	   }else{
               
               userAccountExist = false;
           }
       }
       if (userAccountExist == false){
           
           System.out.println("USER ACCOUNT DOES NOT EXIST\n");
       }
       
       }catch(Exception e){
           
           e.printStackTrace();
       }
   }
   
   /**
    * take user name,old password, new password domain name as input, replace the old
    * password with new password in passwd_file.txt
    * @throws IOException 
    */
   
   public static void change_account(){
       
        //get username from user
       
       Scanner scanner1 = new Scanner(System.in);
           
       System.out.println("Enter the username: ");
           
       String username = scanner1.next();
       
       //get the old password from user
       
       Scanner scanner2 = new Scanner(System.in);
           
       System.out.println("Enter the old password: ");
           
       String oldPassword = scanner2.next();
       
       //get the new password from the user
       
       Scanner scanner3 = new Scanner(System.in);
           
       System.out.println("Enter the new password: ");
           
       String newPassword = scanner3.next();
       
       //get the domain from the user
       
        Scanner scanner4 = new Scanner(System.in);
           
       System.out.println("Enter the domain: ");
           
       String domain = scanner4.next();
       
       //check if the account exsit
       //if it does exist,create a new file to replace passwd_file.txt after modification
       
       try{
    	  
       FileReader fr = new FileReader("passwd_file.txt");
       
       BufferedReader br = new BufferedReader(fr);
       
       String text = "";
           
       Boolean userAccountExist = null;
           
      // create a EncAndDec object to decrypt and encrypt the massage
           
        EncAndDec c_3 = new EncAndDec();
       
       while((text = br.readLine()) != null){
    	   
           
    	   if (c_3.decrypt(text).equals(domain)){
    		   
    		   if (c_3.decrypt(br.readLine()).equals(username)){
                   
                    userAccountExist = true;
                   
                    File temPath = new File("/Users/ruizhao/Desktop/PasswordManager/tem.txt");

                       File deletePath = new File("/Users/ruizhao/Desktop/PasswordManager/passwd_file.txt");

                       if (!temPath.exists()){

                        try{
                            temPath.createNewFile();

                        }catch(Exception e){

                            e.printStackTrace();
                        }
                       }

                       try{

                       FileReader fr2 = new FileReader("passwd_file.txt");

                       BufferedReader br2 = new BufferedReader(fr2);

                       String text3 = "";

                       while((text3 = br2.readLine()) != null){

                           if (c_3.decrypt(text3).equals(oldPassword)){
                               //re-encrypt the massage

                               writeLine("tem.txt", c_3.encrypt(newPassword));
                           }else{

                               writeLine("tem.txt", c_3.encrypt(c_3.decrypt(text3)));
                           }
                       }


                       }catch(Exception e){

                           e.printStackTrace();
                       }
                       //delete the old passwd_file
                       deletePath.delete();

                       //rename the tem file to passwd_file.txt
                       temPath.renameTo(deletePath);
                   
                   
               }
    	   }else{
               
               userAccountExist = false;
           }
       }
       if (userAccountExist == false){
           
           System.out.println("USER ACCOUNT DOES NOT EXIST!\n");
           
       }
       
       }catch(Exception e){
           
           e.printStackTrace();
       }
   }
    
    
   //create a arraylist to store user information
   // get password from clientInformation
   public static void get_password(){
       //go through the clientInformation arraylist to find the domain and return its correspond username
       //and password
       
       Scanner scanner3 = new Scanner(System.in);
           
       System.out.println("Enter the domain: ");
           
       String userDomain = scanner3.next();
       
       FileReader fr = null;
       
       BufferedReader br = null;
       
       try{
           
           fr = new FileReader("passwd_file.txt");
           
           br = new BufferedReader(fr);
           
           String text4 = null;
           
           Boolean userFound = false;
           
           //create a EncAndDec object to decrypt and encrypt
           
           EncAndDec c_4 = new EncAndDec();
            
           while ((text4 = br.readLine()) != null){
               
                if (c_4.decrypt(text4).equals(userDomain)){

                   System.out.println(c_4.decrypt(br.readLine()) + " " + c_4.decrypt(br.readLine()) + "\n");
                    
                   userFound = true;
                    
                   text4 = null;
                    
                    
              }
               
           }
           if (userFound == false){
                    
                   System.out.println("USER ACCOUNT DOES NOT EXIST!\n");
                }
               
           
       }catch(IOException e){
           
           e.printStackTrace();
       }
       
       
       
  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		checkRegistration();
	}

}
