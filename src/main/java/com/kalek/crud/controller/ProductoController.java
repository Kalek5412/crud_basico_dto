package com.kalek.crud.controller;


import ch.qos.logback.core.util.StringCollectionUtil;
import com.kalek.crud.dto.Mensaje;
import com.kalek.crud.dto.ProductoDTO;
import com.kalek.crud.exception.RecursoException;
import com.kalek.crud.models.Producto;
import com.kalek.crud.service.ProductoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Convert;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/listar")
    public List<Producto> listaDeProductos(){
        return  productoService.listarProductos();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Producto> detalleProducto(@PathVariable Long id){
        if(!productoService.existeElId(id)){
            //return new ResponseEntity(new Mensaje("No existe el id"),HttpStatus.NOT_FOUND);
            throw  new RecursoException("no se encontro el producto ");
        }
        Optional<Producto> producto=productoService.buscarPorId(id);
       return new ResponseEntity(producto,HttpStatus.OK);
    }

    @GetMapping("lista/{nombre}")
    public ResponseEntity<Producto> obtenerNombre(@PathVariable String nombre){
        if(!productoService.existeNombre(nombre)){
            return new ResponseEntity(new Mensaje("No existe el nombre"),HttpStatus.NOT_FOUND);
        }
        Producto producto =productoService.buscarPorNombre(nombre).get();
        return new ResponseEntity(producto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO dto){
        if(dto.getPrecio() < 0){
            return new ResponseEntity(new Mensaje("El producto debe sr mayor que 0"),HttpStatus.BAD_REQUEST);
        }
        if(productoService.existeNombre(dto.getNombre())){
            return new ResponseEntity(new Mensaje("Ya existe el producto con ese nombre"),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(dto));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){
        if(!productoService.existeElId(id)){
            return new ResponseEntity(new Mensaje("No existe el id"),HttpStatus.NOT_FOUND);
        }
        if(productoDTO.getPrecio()==null || productoDTO.getPrecio()<0){
            return new ResponseEntity(new Mensaje("El producto debe sr mayor que 0"),HttpStatus.BAD_REQUEST);
        }
        if(productoService.existeNombre(productoDTO.getNombre()) && productoService.buscarPorNombre(productoDTO.getNombre())
                .get().getId() != id){
            return new ResponseEntity(new Mensaje("Ya existe el producto con ese nombre"),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.modificar(id,productoDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!productoService.existeElId(id)){
            return new ResponseEntity(new Mensaje("No existe el id"),HttpStatus.NOT_FOUND);
        }
        productoService.eliminar(id);
        return new ResponseEntity(new Mensaje("producto eliminado"),HttpStatus.OK);
    }
}
