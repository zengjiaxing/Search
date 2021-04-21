package com.cap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cap.form.ChangeParaphraseForm;
import com.cap.form.ParaphraseForm;
import com.cap.pojo.Paraphrase;
import com.cap.mapper.ParaphraseMapper;
import com.cap.service.ParaphraseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@Service
public class ParaphraseServiceImpl extends ServiceImpl<ParaphraseMapper, Paraphrase> implements ParaphraseService {
    public void addParaphrase (ParaphraseForm paraphraseForm) {
        Paraphrase paraphrase = new Paraphrase();
        paraphrase.setInfoId(paraphraseForm.getInfoId());
        paraphrase.setParaphraseTitle(paraphraseForm.getParaphraseTitle());
        paraphrase.setParaphraseContent(paraphraseForm.getParaphraseContent());
        paraphrase.setOrderP(paraphraseForm.getOrder());
        save(paraphrase);
    }

    public void changeParaphrase (ChangeParaphraseForm changeParaphraseForm) {
        Paraphrase paraphrase = getById(changeParaphraseForm.getParaphraseId());
        paraphrase.setParaphraseContent(changeParaphraseForm.getParaphraseContent());
        updateById(paraphrase);
    }
    public List<Paraphrase> getParaphraseList (Long infoId) {
        QueryWrapper<Paraphrase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("info_id", infoId);
        System.out.println("333");
        List<Paraphrase> list = list(queryWrapper);
        System.out.println(list+"2333");
        return list;
    }

}
