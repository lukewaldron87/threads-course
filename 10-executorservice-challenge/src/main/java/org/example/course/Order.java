package org.example.course;

public record Order (
        long id,
        String type,
        int quantity
) {
}
