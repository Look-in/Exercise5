package by.entity.old;

public class Customer {
    private int customId;
    private String login;

    public Customer(int customId, String login) {
        this.customId = customId;
        this.login = login;
    }

    public int getCustomId() {
        return customId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin() {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Customer [customId =" + customId + ", login=" + login + "]";
    }


}
