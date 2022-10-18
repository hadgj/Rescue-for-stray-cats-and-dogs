package com.itheima.service.impl;

import com.itheima.entity.PageBean;
import com.itheima.mapper.PetMapper;
import com.itheima.service.PetService;
import com.itheima.entity.pet;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public  class PetServiceImpl implements PetService {
    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();

    public List<pet> selectAll(){
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        List<pet> pets=mapper.selectAll();
        sqlSession.close();
       return pets;
    }

    public  void add(pet pet){
    SqlSession sqlSession=factory.openSession();
    PetMapper mapper=sqlSession.getMapper(PetMapper.class);
    mapper.add(pet);
    sqlSession.commit();
    sqlSession.close();
}

    @Override
    public void deleteByids(int[] ids) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        mapper.deleteByids(ids);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public PageBean<pet> selectByPage(int currentPage, int pageSize) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        int size=pageSize;
        //5.查询当前页数据
        List<pet> rows=mapper.selectByPage(begin,size);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCount();
        //7.封装PageBean对象
        PageBean<pet> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public PageBean<pet> selectByPageAndCondition(int currentPage, int pageSize, pet pet) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        //4.计算开始索引
        int begin=(currentPage-1)*pageSize;
        //计算查询条目数
        int size=pageSize;
        //处理pet条件，模糊表达式
        String petName=pet.getPetName();
        if(petName!=null && petName.length()>0){
            pet.setPetName("%"+petName+"%");
        }
        String petType=pet.getPetType();
        if(petType!=null && petType.length()>0){
            pet.setPetType("%"+petType+"%");
        }
        String petAddress=pet.getPetAddress();
        if(petAddress!=null && petAddress.length()>0){
            pet.setPetAddress("%"+petAddress+"%");
        }
        //5.查询当前页数据
        List<pet> rows=mapper.selectByPageAndCondition(begin,size,pet);
        //6.查询总记录数
        int totalCount=mapper.selectTotalCountByCondition(pet);
        //7.封装PageBean对象
        PageBean<pet> pageBean=new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        sqlSession.close();
        return pageBean;
    }

    @Override
    public void deleteByid(int id) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        mapper.deleteByid(id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void update(pet pet) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        mapper.update(pet);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public pet selectByid(int id) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        pet pet=mapper.selectByid(id);
        sqlSession.close();
        return pet;
    }

    @Override
    public int selectnewid() {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        int id =mapper.selectnewid();
        sqlSession.close();
        return  id;
    }

    @Override
    public List<pet> selectByotele(String otele) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        List<pet> pets=mapper.selectByotele(otele);
        sqlSession.close();
        return pets;
    }

    @Override
    public List<pet> pquanju(String keyword) {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        List<pet> pets=mapper.pquanju(keyword);
        sqlSession.close();
        return pets;
    }

    @Override
    public List<pet> petfour() {
        SqlSession sqlSession=factory.openSession();
        PetMapper mapper=sqlSession.getMapper(PetMapper.class);
        List<pet> pets=mapper.petfour();
        sqlSession.close();
        return pets;
    }
}
