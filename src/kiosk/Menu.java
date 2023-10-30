package kiosk;

public class Menu {
    private final String name;
    private final String description;
    
    public String toString(int i) {
    	String menuName = "%d. %-8s" .formatted(i, this.getName());
    	String menuDescription = "| %s" .formatted(this.getDescription());
    	return menuName + menuDescription;
    }
    
    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }

    /*public void setName(String name) {
        this.name = name;
    }*/

    public String getDescription() {
        return description;
    }

    /*public void setDescription(String description) {
        this.description = description;
    }
    // Getter 및 Setter 메서드, 기타 메서드는 필요에 따라 추가
     
     */
}
