package com.example.javaexamblogapi.dto;

import com.example.javaexamblogapi.model.Post;
import lombok.Data;

@Data
public class PostDto implements ObjectDto<Post> {

    private Long id;
    private String title;
    private String content;

    @Override
    public Post toEntity() {
        Post result = new Post();

        result.setTitle(title);
        result.setContent(content);

        return result;
    }

    public static PostDto fromEntity(Post entity) {
        PostDto result = new PostDto();

        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setContent(entity.getContent());

        return result;
    }

    public static PostDto fromEntity(PostRequestDto postRequestDto) {
        PostDto result = new PostDto();

        result.setTitle(postRequestDto.getTitle());
        result.setContent(postRequestDto.getContent());

        return result;
    }
}
