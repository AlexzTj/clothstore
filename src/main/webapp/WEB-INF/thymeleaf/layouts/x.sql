SELECT
  product0_.id            AS id1_2_0_,
  imagemetas1_.id         AS id1_1_1_,
  product0_.category_id   AS category7_2_0_,
  product0_.description   AS descript2_2_0_,
  product0_.price         AS price3_2_0_,
  product0_.productCode   AS productC4_2_0_,
  product0_.timestamp     AS timestam5_2_0_,
  product0_.title         AS title6_2_0_,
  imagemetas1_.imagePath  AS imagePat2_1_1_,
  imagemetas1_.product_id AS product_5_1_1_,
  imagemetas1_.title      AS title3_1_1_,
  imagemetas1_.type       AS type4_1_1_,
  imagemetas1_.product_id AS product_5_1_0__,
  imagemetas1_.id         AS id1_1_0__
FROM Product product0_ LEFT OUTER JOIN Image imagemetas1_ ON product0_.id = imagemetas1_.product_id
WHERE product0_.id = ?