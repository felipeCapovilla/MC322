package interfaces;

public interface Attacker {

    /**
     * Método para atacar outra entidade destrutível
     * @param ent Entidade atacada
     * @param damage Dano
     */
    public void atacar(Destructible ent, int damage);

}
