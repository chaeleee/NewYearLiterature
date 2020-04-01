package party.of.newyearliterature.role;

/**
 * RoleType
 */
public enum RoleBasicType {

    
    ADMIN("ADMIN"), 
    USER("USER");
    private String name;

    RoleBasicType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}