package com.mbtitalkbackend.post.service;

import com.mbtitalkbackend.post.mapper.PostMapper;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public PostVO findPostEntityById(long postId) {

        try {
            postMapper.increaseViewCount(postId);

            PostEntity postEntity = postMapper.findPostEntityByPostId(postId);

            return PostVO.of(postEntity);
        }
        catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public Integer createPost(PostVO postVO) {

        PostEntity postEntity = PostEntity.create(postVO);

        return postMapper.postPost(postEntity);
    }

    public Integer patchPostById(long postId, PostVO postVO) {

        postMapper.updatePostModifiedTime(postId);
        PostEntity postEntity = postMapper.findPostEntityByPostId(postId);

        if(postEntity == null)
            return 0;

        postEntity.setBoardId(postVO.getBoardId());
        postEntity.setMemberId(postVO.getMemberId());
        postEntity.setTitle(postVO.getTitle());
        postEntity.setContent(postVO.getContent());


        return postMapper.updatePost(postEntity);
    }

    public Integer deletePostById(long postId) {
        return postMapper.deletePostByPostId(postId);
    }
}
