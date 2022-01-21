package com.mbtitalkbackend.board.service;

import com.mbtitalkbackend.board.mapper.BoardMapper;
import com.mbtitalkbackend.board.model.PagingCriteria;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<PostEntity> listAllPosts() {

        return boardMapper.findAllPosts();
    }

    public List<PostVO> listAllPostsWithPaging(PagingCriteria pagingCriteria) {

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithPaging(pagingCriteria);

        List<PostVO> postVOList = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            postVOList.add(PostVO.of(postEntity));
        }

        return postVOList;
    }

    public List<PostVO> listAllPostsWithMBTI(PagingCriteria pagingCriteria, String mbti) {

        List<PostEntity> postEntityList = boardMapper.findAllPostsWithMBTI(pagingCriteria, mbti);

        List<PostVO> postVOList = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            postVOList.add(PostVO.of(postEntity));
        }

        return postVOList;
    }
}
