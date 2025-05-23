package interfaces;

public interface Destructible {

    /**
     * Método que tira vida da entidade
     * @param damage
     */
    public void takeDamage(int damage);

    /**
     * Método que aumenta a vida da entidade
     */
    public void repairLife(int repair);

}
