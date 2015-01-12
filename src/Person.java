

import java.util.*;
import java.io.*;



public class Person {
	 
	 String name, school;
	 int vertex;
	 ArrayList<Integer> friends;
	 int dfsNum, back;
	 boolean visited;
	 
	 public Person(String newName, int newVertex)
	 {
	  name = newName;
	  vertex = newVertex;
	  friends = new ArrayList<Integer>();
	 }
	 
	 public Person(String newName, String newSchool, int newVertex)
	 {
	  name = newName;	 
	  school = newSchool;
	  vertex = newVertex;
	  friends = new ArrayList<Integer>();
	 }
	 
	 public String toString()
	 {
	  if(school == null)
	   return "Name: " + name;
	  return "Name: " + name + " School: " + school;
	 }

	}
