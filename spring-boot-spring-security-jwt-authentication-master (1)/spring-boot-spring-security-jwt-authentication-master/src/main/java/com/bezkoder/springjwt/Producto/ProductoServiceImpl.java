package com.bezkoder.springjwt.Producto;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.Base.BaseServiceImpl;
import com.bezkoder.springjwt.Categoria.Categoria;
import com.bezkoder.springjwt.Categoria.CategoriaServiceImpl;
import com.bezkoder.springjwt.ImageStorage.IndireccionImageStorage;
import com.bezkoder.springjwt.Producto.DTO.DTOCreateProducto;
import com.bezkoder.springjwt.Producto.DTO.DTOUpdateProducto;
import java.util.Optional;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto, Long, ProductoRepository>
        implements ProductoService {

    CategoriaServiceImpl categoriaService;
    @Autowired
    public ProductoRepository repo;

    @Autowired
    public ProductoServiceImpl(ProductoRepository repository, CategoriaServiceImpl categoriaService) {
        super(repository);
        this.categoriaService = categoriaService;
    }

    @Override
    public Producto save(DTOCreateProducto productoNuevo) throws Exception {
        try {
            System.out.println(productoNuevo.getCategoria());
            // Convierto los campos basicos del dto a objeto
            ModelMapper mapper = new ModelMapper();
            Producto producto = mapper.map(productoNuevo, Producto.class);
            System.out.println(productoNuevo.getCategoria());
            // Cargo la categoria (Me aseguro que exista o la creo)
            Categoria categoria = categoriaService.findById(productoNuevo.getCategoria());

            producto.setCateogria(categoria);
            // La imagen se va a un microservicio de nodejs y me devuelve la url de un
            // storage de firebase
            IndireccionImageStorage storage = new IndireccionImageStorage();
            String direccionImg = storage.uploadImage(productoNuevo.getImagen(), producto.getNombre());

            producto.setImagen(direccionImg);
            return repository.save(producto);
        } catch (Exception e) {
            throw new Exception();
        }
    }

     public Producto update(DTOUpdateProducto p) throws Exception {
        try {
            Producto producto = this.repository.findById(p.getId()).get();
            producto.setNombre(p.getNombre());
            producto.setPrecio(p.getPrecio());
            producto.setStock(p.getStock());
            Categoria cat = this.categoriaService.findById(p.getCategoria());
            producto.setCateogria(cat);
            return repository.save(producto);
        } catch (Exception e) {
            throw new Exception();
        }
    }
     
    public List<Producto> findWithFilters(
            String nombre, Long categoria) {

        if (nombre == null && categoria == null)
            return repository.findAllDisponibles();

        if (nombre == null)
            return this.findAllByCategory(categoria);

        return repository.findByNombreContaining(nombre);
    }

    public List<Producto> findAllByCategory(Long categoria) {
        if (categoria == null) {
            return repository.findAllDisponibles();
        }
        return repository.findByCateogriaId(categoria);

    }

    public List<Producto> findAllByName(String name) {
        return repository.findByNombreContaining(name);
    }

    // Busca similares pero no el mismo item
    public List<Producto> findSimilar(Producto producto) {
        Categoria categoria = producto.getCateogria();
        List<Producto> similares = repository.findByCateogriaId(categoria.getId());

        return similares;
    }
   
    public Producto findById(Long id){
        return this.repository.findById(id).get();
    }
    
    public void Save(Producto p){
        this.repository.save(p);
    }
    
    public List<Producto> findAllBySinStock(){
        return this.repository.findAllBySinStock();
    }
}
