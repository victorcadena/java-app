package com.mycompany.importserver;


/**
 *
 * @author ochoa
 */


public class NameHandler {
    
    private String name;
    private String lastname;
    private String email;

    /** Creates a new instance of NameHandler */
    public NameHandler() {
       name = null;
       lastname = null;
    }

    public String getName() {
       return name;
    }

    public void setName(String name) {
       this.name = name;
    }
    
    public String getLastname() {
       return lastname;
    }

    public void setLastname(String lastname) {
       this.lastname = lastname;
    }
    
    public String getEmail() {
       return email;
    }

    public void setEmail(String email) {
       this.email = email;
    }
    
}
