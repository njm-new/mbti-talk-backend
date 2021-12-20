package com.mbtitalkbackend.post.service;

import com.mbtitalkbackend.post.mapper.PostMapper;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostMapper postMapper;

    @Override
    public PostEntity findPostEntityById(long post_id) {
        postMapper.increaseViewCount(post_id);
        return postMapper.findPostEntityByPostId(post_id);
    }

    @Override
    public Integer createPost(PostVO postVO) {

        PostEntity postEntity = new PostEntity(postVO.getBoard_id(),
                postVO.getMember_id(),
                postVO.getTitle(),
                postVO.getContent(),
                0,
                0);

        return postMapper.postPost(postEntity);
    }

    @Override
    public Integer patchPostById(long post_id, PostVO postVO) {

        PostEntity postEntity = postMapper.findPostEntityByPostId(post_id);

        if(postEntity == null)
            return 0;

        postEntity.setBoard_id(postVO.getBoard_id());
        postEntity.setMember_id(postVO.getMember_id());
        postEntity.setTitle(postVO.getTitle());
        postEntity.setContent(postVO.getContent());

        return postMapper.updatePost(postEntity);
    }

    @Override
    public Integer deletePostById(long post_id) {
        return postMapper.deletePostByPostId(post_id);
    }
}
