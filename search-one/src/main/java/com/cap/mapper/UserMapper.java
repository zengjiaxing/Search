package com.cap.mapper;

import com.cap.pojo.CollectionData;
import com.cap.pojo.Record;
import com.cap.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User queryUser(String name);
    int addUser(User user);
    int addRecord(Record record);
    int updateRecord(Record record);
    Record queryRecord(Record record);
    List<String> queryRecordByUserId(int userId);
    CollectionData queryCollection(CollectionData collectionData);
    int addCollection(CollectionData collectionData);
    int deleteCollection(CollectionData collectionData);
    List<String> queryCollectionByUserId(int userId);
}
