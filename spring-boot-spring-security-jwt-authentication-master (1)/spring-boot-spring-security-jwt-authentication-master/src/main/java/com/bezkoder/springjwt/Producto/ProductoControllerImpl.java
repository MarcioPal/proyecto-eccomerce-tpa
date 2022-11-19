package com.bezkoder.springjwt.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.springjwt.Base.BaseControllerImpl;
import com.bezkoder.springjwt.Producto.DTO.DTOCreateProducto;
import com.bezkoder.springjwt.Producto.DTO.DTOUpdateProducto;
import java.time.LocalDateTime;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/producto")
public class ProductoControllerImpl
        extends BaseControllerImpl<Producto, Long, ProductoServiceImpl>
        implements ProductoController {

    @Autowired
    public ProductoService productoServ;

    @Autowired
    public ProductoControllerImpl(ProductoServiceImpl service) {
        super(service);
    }

    @PostMapping(path = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Producto save(DTOCreateProducto entity) throws Exception {

        return this.service.save(entity);
    }

    @PostMapping("/prueba")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public int probando(@ModelAttribute DTOCreateProducto dto) {
        System.out.println(dto.getNombre());
        return 1;
    }
    
    @PostMapping("/admin/producto/darBaja/{id}")
     public void darBajaProducto(@PathVariable Long id){
         Producto p = this.service.findById(id);
         p.setFechaHoraBaja(LocalDateTime.now());
         this.service.Save(p);
     }
     
     @PostMapping("/actualizar")
     public String actualizarProducto(@RequestBody DTOUpdateProducto producto){
         try{
         this.service.update(producto);
         return "producto actualizado correctamente";
         }catch(Exception e){
             return "se ha producido un error";
         }
     }

}
