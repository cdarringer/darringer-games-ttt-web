Steps to Run:
=============
1. Import it as a maven project in eclipse IDE.

2. Running the application through Servlet
   * Goto the main project "darringer-games-ttt-web", right click for Run As->Maven build (This will generate war file)
   * Right click on the main project "darringer-games-ttt-web", select again Run As-> Run on Server
   * In "Run on Server" window, select "Manually define a new server" 
   * In server type, selct Basic->J2EE Preview->Next->Finish
   * A http page is opened in eclipse, copy the link and paste in internet browser(http://localhost:8080/darringer-games-ttt-web)
   * Add TTTServlet word at the end of link to play (http://localhost:8080/darringer-games-ttt-web/TTTServlet)
   
3. Running the application through console
   * Right click on the main project "darringer-games-ttt-web", select Run As-> Java Application
   * Select Java application as "TTLMain-com.darringer.games.ttt.main"
   * Then play by giving input the grid value through 0-8
   
4. To find coverage(Prerequisite:- Eclemma branch coverage tool to be installed in eclipse)
   * Right click on the main project "darringer-games-ttt-web", select again Run As-> Coverage As->Coverage Configuration
   * In Coverage Configuration window , in Coverage tab, uncheck all class except for "darringer-games-ttt-web-src/main/java"
     (As we are interested to see the coverage of main java application)
   

Project Structure:
==================
Unit test cases are added to increase the code coverage in the following package as follows,
 - com.darringer.games.ttt.control
   * TTTGameControllerTest.java
   
 - com.darringer.games.ttt.model
   * TTTGameStatusTest.java
   * TTTModelTest.java
   
 - com.darringer.games.ttt.web
   * TTTJSPViewTest.java
   * TTTServletTest.java