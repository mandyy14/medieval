package com.fiap.medieval.repository;

import com.fiap.medieval.model.Item;
import com.fiap.medieval.model.enums.ItemRaridade;
import com.fiap.medieval.model.enums.ItemTipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    Page<Item> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Item> findByTipo(ItemTipo tipo, Pageable pageable);

    Page<Item> findByRaridade(ItemRaridade raridade, Pageable pageable);

    Page<Item> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax, Pageable pageable);

    Page<Item> findByDonoId(Long personagemId, Pageable pageable);
}
