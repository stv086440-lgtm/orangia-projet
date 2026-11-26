package com.orangia.dao.interfaces;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * Lecture du stock disponible via la vue {@code V_STOCK_FERME}.
 *
 * <p>Le planning ne définit pas de Model « Stock » : les valeurs sont des
 * quantités {@link BigDecimal}, comme {@code Recolte.quantiteKg} et
 * {@code Expedition.quantiteKg}. Aucun DTO supplémentaire n'est introduit.</p>
 */
public interface StockDAO {

    /**
     * Lit le {@code stock_disponible} d'une ferme.
     *
     * @param idFerme identifiant de la ferme
     * @return le stock, ou {@link Optional#empty()} si la ferme est absente de la vue
     */
    Optional<BigDecimal> findStockByFermeId(Long idFerme);

    /**
     * Lit le stock de toutes les fermes présentes dans la vue.
     *
     * @return une map {@code idFerme -> stock_disponible}, vide si aucune ferme
     */
    Map<Long, BigDecimal> findAllStock();
}
