package com.mello.mello.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRatings {

    private User rater;
    private User beingRated;
    private int rating;
}
