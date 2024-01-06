package com.kalek.crud.service;

import com.kalek.crud.dto.ProductoDTO;
import com.kalek.crud.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    public Optional<Producto> buscarPorNombre(String nombre);

    public boolean existeNombre(String nombre);

    public List<Producto> listarProductos();
    public Optional<Producto> buscarPorId(Long id);
    public Producto guardar(ProductoDTO objDTO);

    public Producto modificar(Long id, ProductoDTO objDTO);

    public void eliminar(Long id);

    public boolean existeElId(Long id);


}
