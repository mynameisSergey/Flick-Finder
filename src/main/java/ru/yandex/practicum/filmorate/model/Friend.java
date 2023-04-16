package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @NonNull
    private Integer userId;
    @NonNull
    private Integer friendId;

    private Boolean status;
}