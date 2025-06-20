package app;

public class Persona {
    // country Code
   public String description;

   // country Name
   public String image;

   /**
    * Create a Country and set the fields
    */
   public Persona(String description, String image) {
      this.description = description;
      this.image = image;
   }

   public String getDescription() {
      return description;
   }

   public String getImage() {
      return image;
   }
}
