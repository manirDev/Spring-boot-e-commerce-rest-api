package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "WITH RECURSIVE ancestors(id, parent_id, name, lvl) AS ("
                    + "   SELECT cat.id, cat.parent_id, cat.name, 1 AS lvl "
                    + "   FROM categories cat "
                    + "   WHERE cat.id = :categoryId "
                    + "   UNION ALL "
                    + "   SELECT parent.id, parent.parent_id, parent.name, child.lvl + 1 AS lvl "
                    + "   FROM categories parent "
                    + "   JOIN ancestors child "
                    + "   ON parent.id = child.parent_id "
                    + " )"
                    + "SELECT category_name from ancestors ORDER BY lvl DESC"
            , nativeQuery = true)
    List<String> findAncestry(@Param("categoryId") Long categoryId);

//    @Query("select distinct c from Category c " +
//            "left join fetch c.parent cc " +
//            "left join fetch c.children ch " +
//            "where cc is null")
//    List<Category> findAll();

}
