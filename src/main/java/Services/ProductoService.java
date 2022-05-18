package Services;

import Models.Producto;
import Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto create(Producto producto){
       return productoRepository.save(producto);
    }

    public List<Producto> getAllProducts(){
        return productoRepository.findAll();
    }

    public void delete(Producto producto){
        productoRepository.delete(producto);
    }

    public Optional<Producto> findByID(Long id){
        return productoRepository.findById(id);
    }
}
