package com.nighthawk.spring_portfolio.mvc.calendar;

/** Simple POJO 
 * Used to Interface with APCalendar
 * The toString method(s) prepares object for JSON serialization
 * Note... this is NOT an entity, just an abstraction
 */
class Year {
   private int year;
   private int year1;
   private int year2;
   private int month;
   private int day;
   private boolean isLeapYear;
   private int firstDayOfYear;
   private int dayOfYear;
   private int numberOfLeapYears;
   private int dayOfWeek;


   // zero argument constructor
   public Year() {} 

   /* year getter/setters */
   public int getYear() {
      return year;
   }
   public void setYear(int year) {
      this.year = year;
      this.setIsLeapYear(year);
      this.setfirstDayOfYear(year);
   }

   /* years getter/setters */
   public int getYears() {
      return year;
   }
   public void setYears(int year1, int year2) {
      this.year1 = year1;
      this.year2 = year2;
      this.setnumberOfLeapYears(year1, year2);
   }

   /* date getter/setters */
   public int getDate() {
      return year;
   }
   public void setDate(int month, int day, int year) {
      this.month = month;
      this.day = day;
      this.year = year;
      this.setdayOfYear(month, day, year);
      this.setdayOfWeek(month, day, year);
   }

   /* isLeapYear getter/setters */
   public boolean getIsLeapYear(int year) {
      return APCalendar.isLeapYear(year);
   }
   private void setIsLeapYear(int year) {  
      this.isLeapYear = APCalendar.isLeapYear(year);
   }

   /* firstDayofYear getter/setters */
   public int getfirstDayofYear(int year) {
      return APCalendar.firstDayOfYear(year);
   }
   private void setfirstDayOfYear(int year) {  
      this.firstDayOfYear = APCalendar.firstDayOfYear(year);
   }

   /* numberOfLeapYears getter/setters */
   public int getnumberOfLeapYears(int year1, int year2) {
      return APCalendar.numberOfLeapYears(year1, year2);
   }
   private void setnumberOfLeapYears(int year1, int year2) {  
      this.numberOfLeapYears = APCalendar.numberOfLeapYears(year1, year2);
   }

   /* dayOfYear getter/setters */
   public int getdayOfYear(int month, int day, int year) {
      return APCalendar.dayOfYear(month, day, year);
   }
   private void setdayOfYear(int month, int day, int year) {  
      this.dayOfYear = APCalendar.dayOfYear(month, day, year);
   }

   /* dayOfWeek getter/setters */
   public int getdayOfWeek(int month, int day, int year) {
      return APCalendar.dayOfWeek(month, day, year);
   }
   private void setdayOfWeek(int month, int day, int year) {  
      this.dayOfWeek = APCalendar.dayOfWeek(month, day, year);
   }

   /* isLeapYearToString formatted to be mapped to JSON */
   public String isLeapYearToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"isLeapYear\": "  +this.isLeapYear+ " }" );
   }	

   public String firstDayOfYearToString(){
      return ( "{ \"year\": "  +this.year+  ", " + "\"firstDayOfYear\": "  +this.firstDayOfYear+ " }" );
   }

   public String numberOfLeapYearsToString(int year, int year2) {
      return ("{ \"year1\": " + year1 + ", " + "\"year2\": " + year2 + ", " + "\"numberOfLeapYears\": "
            + getnumberOfLeapYears(year1, year2) + " }");
   }

   public String dayOfYearToString() {
      return ("{ \"month\": " + this.month + ", " + "\"day\": " + this.day + ", " + "\"year\": " + this.year + ", "
            + "\"dayOfYear\": " + this.dayOfYear + " }");
   }

   public String dayOfWeekToString() {
      return ("{ \"month\": " + this.month + ", " + "\"day\": " + this.day + ", " + "\"year\": " + this.year + ", "
            + "\"dayOfWeek\": " + this.dayOfWeek + " }");
   }

   /* standard toString placeholder until class is extended */
   public String toString() { 
      return isLeapYearToString(); 
   }

   public static void main(String[] args) {
      Year year = new Year();
      year.setYear(2022);
      year.setDate(3, 5, 2028);
      year.setYears(2000, 2050);
      System.out.println(year);
   }
}