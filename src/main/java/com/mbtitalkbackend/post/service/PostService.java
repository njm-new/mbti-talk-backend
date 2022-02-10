package com.mbtitalkbackend.post.service;

import com.mbtitalkbackend.like.mapper.LikeMapper;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.post.mapper.PostMapper;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final MemberMapper memberMapper;
    private final LikeMapper likeMapper;

    public PostVO findPostEntityById(String postId) {

        try {
            postMapper.increaseViewCount(postId);

            PostEntity postEntity = postMapper.findPostEntityByPostId(postId);
            MemberEntity memberEntity = memberMapper.findMemberById(postEntity.getMemberId());
            final boolean like = likeMapper.findLike(LikeEntity.create(postId, memberEntity.getMemberId())) != null;
            //todo: Post boardId와 member Mbti 비교 필요

            return PostVO.of(
                    postEntity,
                    memberEntity,
                    like
            );
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    public Integer createPost(PostVO postVO) {

        PostEntity postEntity = PostEntity.create(postVO);

        return postMapper.postPost(postEntity);
    }

    public Integer patchPostById(String postId, PostVO postVO) {

        postMapper.updatePostModifiedTime(postId);
        PostEntity postEntity = postMapper.findPostEntityByPostId(postId);

        if (postEntity == null)
            return 0;

        postEntity.setBoardId(postVO.getBoardId());
        postEntity.setMemberId(postVO.getMemberId());
        postEntity.setTitle(postVO.getTitle());
        postEntity.setContent(postVO.getContent());


        return postMapper.updatePost(postEntity);
    }

    public Integer deletePostById(String postId) {
        return postMapper.deletePostByPostId(postId);
    }
}
