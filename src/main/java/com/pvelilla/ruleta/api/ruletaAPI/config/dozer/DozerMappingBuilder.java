package com.pvelilla.ruleta.api.ruletaAPI.config.dozer;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

public class DozerMappingBuilder {

	private DozerBeanMapper dozerBeanMapper;

	public DozerMappingBuilder() {
		this.dozerBeanMapper = dozerBeanMapper();
	}

	private DozerBeanMapper dozerBeanMapper() {
		List<String> mappingFiles = new ArrayList<>();
		mappingFiles.add("dozerJdk8Converters.xml");

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(mappingFiles);
		mapper.addMapping(beanMappingBuilder());
		return mapper;
	}

	public <T> T map(Object source, Class<T> destinationClass) {
		return dozerBeanMapper.map(source, destinationClass);
	}

	private BeanMappingBuilder beanMappingBuilder() {
		return new BeanMappingBuilder() {

			@Override
			protected void configure() {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

}
