package com.kalek.crud.service;

import com.kalek.crud.dto.ProductoDTO;
import com.kalek.crud.models.Producto;
import com.kalek.crud.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardar(ProductoDTO objDTO) {
        Producto producto =new Producto();
        producto.setNombre(objDTO.getNombre());
        producto.setPrecio(objDTO.getPrecio());;
        return productoRepository.save(producto);
    }

    @Override
    public Producto modificar(Long id, ProductoDTO objDTO) {
        Producto producto = productoRepository.findById(id).get();
        producto.setNombre(objDTO.getNombre());
        producto.setPrecio(objDTO.getPrecio());;
        return productoRepository.save(producto);
    }


    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);

    }

    @Override
    public boolean existeElId(Long id) {
        return productoRepository.existsById(id);
    }

    @Override
    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public boolean existeNombre(String nombre) {
        return productoRepository.existsByNombre(nombre);
    }


}
