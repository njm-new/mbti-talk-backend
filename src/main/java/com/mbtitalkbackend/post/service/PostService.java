package com.mbtitalkbackend.post.service;

import com.mbtitalkbackend.comment.mapper.CommentMapper;
import com.mbtitalkbackend.like.mapper.LikeMapper;
import com.mbtitalkbackend.like.model.entity.LikeEntity;
import com.mbtitalkbackend.member.mapper.MemberMapper;
import com.mbtitalkbackend.member.model.entity.MemberEntity;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.post.mapper.PostMapper;
import com.mbtitalkbackend.post.model.Entity.PostEntity;
import com.mbtitalkbackend.post.model.VO.PostVO;
import com.mbtitalkbackend.util.generator.IdGenerator;
import com.mbtitalkbackend.util.generator.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final MemberMapper memberMapper;
    private final LikeMapper likeMapper;
    private final CommentMapper commentMapper;
    private final IdGenerator idGenerator;

    public PostVO findPostEntityById(String postId, Member member) {

        try {
            postMapper.increaseViewCount(postId);

            PostEntity postEntity = postMapper.findPostEntityByPostId(postId);
            MemberEntity memberEntity = memberMapper.findMemberById(postEntity.getMemberId());
            final boolean like = likeMapper.findLike(LikeEntity.create(postId, member.getMemberId())) != null;
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

    public PostVO createPost(PostVO postVO, Member member) throws Exception {
        String postId = idGenerator.generate(ServiceType.POST);

        PostEntity postEntity = PostEntity.create(postVO, postId);

        int res = postMapper.postPost(postEntity);
        //useGeneratedKeys를 사용하여 postEntity에 id가 할당됨
        //참고) https://hochoon-dev.tistory.com/entry/SpringBoot-Mybatis-Insert-%ED%95%9C-%EA%B0%92%EC%9D%98-AUTOINCREMENT%EB%90%9C-ID-%EA%B0%80%EC%A0%B8%EC%98%A4%EA%B8%B0
        //TODO idGenerator를 어플리케이션 레벨에서 생성 (yyyyMMdd-"keyword(picture|post|member)"-mysqlsequence활용)

        if (res > 0) {
            return findPostEntityById(postEntity.getPostId(), member);
        } else {
            // TODO 1. exception 적당하게 바꾸기
            // TODO 2. 보통 생성이 안되면 mapper에서 exception을 던지는걸로 알고 있음 (현재 res > 0 으로 확인중)
            throw new Exception("생성 실패");
        }
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
        commentMapper.deleteCommentByPostId(postId);
        return postMapper.deletePostByPostId(postId);
    }
}
