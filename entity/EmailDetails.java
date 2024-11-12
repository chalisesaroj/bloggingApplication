// Java Program to Illustrate EmailDetails Class
 
package com.udemy.learn.blogging.entity;
 
// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
 
// Class
public class EmailDetails {
 
    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    String userenteredcode;
 
}
//Step 4: Creating interface EmailService and implementing class EmailSe