package com.library.book.mapper;

import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper;


    //MOdel mapper bir mapperdır. Mapper ise classlar arası dönüşüm yapan araçlardır. Bunların amacı api dönüşlerinde
    // veya requestlerde farklı gelen dtoları asıl entitye döşüm işlemi yapmaktır.
    // Bunun nedeni ise bazen kullanıcıya iç tarafı ilgilendiren ancak kullanıcıyı ilgilendirmeyen bilgileri vermemektir. Yani bir takım enkapsulasyondur.
    // Bu durumlar için mapperlar kullanılır
    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T> T convert(Object objectToBeConverted, T convertedObject) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(objectToBeConverted, (Type) convertedObject.getClass());
    }

}
