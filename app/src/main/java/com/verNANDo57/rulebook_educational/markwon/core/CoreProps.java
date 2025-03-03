/*
 * Author: noties <https://noties.io/>
 * date: 2021/03/15
 *
 * Modified by: VerNANDo57 <silvenation@gmail.com>
 * date: 2022/01/24 6:01PM GMT+7
 */

package com.verNANDo57.rulebook_educational.markwon.core;

import com.verNANDo57.rulebook_educational.markwon.Prop;

/**
 * @since 3.0.0
 */
public abstract class CoreProps {

    public static final Prop<ListItemType> LIST_ITEM_TYPE = Prop.of("list-item-type");

    public static final Prop<Integer> BULLET_LIST_ITEM_LEVEL = Prop.of("bullet-list-item-level");

    public static final Prop<Integer> ORDERED_LIST_ITEM_NUMBER = Prop.of("ordered-list-item-number");

    public static final Prop<Integer> HEADING_LEVEL = Prop.of("heading-level");

    public static final Prop<String> LINK_DESTINATION = Prop.of("link-destination");

    public static final Prop<Boolean> PARAGRAPH_IS_IN_TIGHT_LIST = Prop.of("paragraph-is-in-tight-list");

    /**
     * @since 4.1.1
     */
    public static final Prop<String> CODE_BLOCK_INFO = Prop.of("code-block-info");

    public enum ListItemType {
        BULLET,
        ORDERED
    }

    private CoreProps() {
    }
}
