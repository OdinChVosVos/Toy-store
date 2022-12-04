package ru.ds.education.testspringboot.core.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ds.education.testspringboot.api.job.ImageUtils;
import ru.ds.education.testspringboot.api.job.NullProperties;
import ru.ds.education.testspringboot.core.mapper.BookedMapper;
import ru.ds.education.testspringboot.core.mapper.CategoryMapper;
import ru.ds.education.testspringboot.core.model.BookedDto;
import ru.ds.education.testspringboot.core.model.TovarDto;
import ru.ds.education.testspringboot.db.entity.Tovar;
import ru.ds.education.testspringboot.db.repository.BookedRepository;
import ru.ds.education.testspringboot.db.repository.CategoryRepository;
import ru.ds.education.testspringboot.db.repository.TovarRepository;
import ru.ds.education.testspringboot.core.mapper.TovarMapper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TovarService {

    @Autowired
    private TovarRepository tovarRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TovarMapper tovarMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RemindService remindService;

    @Autowired
    private BookedMapper bookedMapper;

    @Autowired
    private BookedRepository bookedRepository;


    public TovarDto addTovar(Long category, String name, int cost, int quantity, String description, MultipartFile file) throws IOException {
        Tovar newTovar = tovarRepository.save(Tovar.builder()
                .category(categoryRepository.getById(category))
                .name(name)
                .cost(cost)
                .quantity_in_stock(quantity)
                .description(description)
                .photo(ImageUtils.compressImage(file.getBytes())).build()
        );

        return tovarMapper.map(newTovar, TovarDto.class);
    }

    public List<TovarDto> putGoods(List<TovarDto> goods){
        List<TovarDto> newGoods = new ArrayList<>();

        for (TovarDto good:goods) {
            Tovar existingTovar = tovarRepository.getById(good.getId());
            BeanUtils.copyProperties(good, existingTovar, NullProperties.getNullPropertyNames(good));
            newGoods.add(tovarMapper.map(tovarRepository.saveAndFlush(existingTovar), TovarDto.class));
        }
        remindService.check();
        return newGoods;
    }

    public void putGood(Long id, MultipartFile file) throws IOException{
        Tovar existingTovar = tovarRepository.getById(id);
        Tovar tovar = new Tovar(existingTovar.getCategory(), existingTovar.getName(),
                existingTovar.getCost(), existingTovar.getQuantity_in_stock(),
                existingTovar.getDescription(), ImageUtils.compressImage(file.getBytes()));
        BeanUtils.copyProperties(tovar, existingTovar, "id");
        tovarRepository.saveAndFlush(existingTovar);
    }

    public ResponseEntity<byte[]> downloadImg(Long id){
        TovarDto tovar = getTovar(id);
        byte[] img = ImageUtils.decompressImage(tovar.getPhoto());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(img);
    }


    public List<TovarDto> getAll(){
        return tovarMapper.mapAsList(tovarRepository.findAll(), TovarDto.class);
    }

    public List<TovarDto> getCategory(int category){
        return tovarMapper.mapAsList(tovarRepository.findByCategory(category), TovarDto.class);
    }

    public TovarDto getTovar(Long id){
        return tovarMapper.map(tovarRepository.getById(id), TovarDto.class);
    }


    @Transactional
    public void deBook(Long userId) throws InterruptedException {
        List<BookedDto> cart = bookedMapper.mapAsList(bookedRepository.getByUser(userId), BookedDto.class);

        for (BookedDto elem:cart) {
            tovarRepository.bookGoods(
                    elem.getTovar().getQuantity_in_stock()+elem.getBookedQuantity(),
                    elem.getTovar().getId()
            );
            bookedRepository.deleteBooked(elem.getId());
        }

    }



}
