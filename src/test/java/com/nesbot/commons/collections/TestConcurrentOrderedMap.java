package com.nesbot.commons.collections;

import com.nesbot.commons.lang.Strings;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

public class TestConcurrentOrderedMap
{
   private ConcurrentOrderedMap<String, Post> posts = new ConcurrentOrderedMap<String, Post>();
   private Post post = new Post("my-cool-post", "My Cool Post");
   private Post post2 = new Post("my-cool-post2", "My Cool Post2");
   private Post post3 = new Post("my-cool-post3", "My Cool Post3");

   @Test
   public void testSize()
   {
      assertEquals(0, posts.size());
      posts.append(post.slug, post);
      assertEquals(1, posts.size());
      posts.append(post2.slug, post2);
      assertEquals(2, posts.size());
   }
   @Test(expected = NullPointerException.class)
   public void testAppendNullKey()
   {
      posts.append(null, post);
   }
   @Test(expected = NullPointerException.class)
   public void testAppendNullValue()
   {
      posts.append(post.slug, null);
   }
   @Test
   public void testAppendDupKey()
   {
      assertTrue(posts.append(post.slug, post));
      assertEquals(1, posts.size());
      assertFalse(posts.append(post.slug, post));
      assertEquals(1, posts.size());
   }
   @Test(expected = IndexOutOfBoundsException.class)
   public void testGetByIndexOutOfBounds()
   {
      posts.get(0);
   }
   @Test(expected = IndexOutOfBoundsException.class)
   public void testGetByIndexOutOfBoundsWithOneElement()
   {
      posts.append(post.slug, post);
      posts.get(1);
   }
   @Test
   public void testGetByIndex()
   {
      posts.append(post.slug, post);
      posts.append(post2.slug, post2);

      // posts should be => [ post, post2 ]

      Post found = posts.get(0);
      assertEquals(found, post);

      found = posts.get(1);
      assertEquals(found, post2);
   }
   @Test
   public void testGetByKeyNotFound()
   {
      posts.append(post.slug, post);
      posts.append(post2.slug, post2);

      // posts should be => [ post, post2 ]

      assertNull(posts.get("nf"));
   }
   @Test
   public void testGetByKey()
   {
      posts.append(post.slug, post);
      posts.append(post2.slug, post2);
      assertEquals(2, posts.size());

      // posts should be => [ post, post2 ]

      Post found = posts.get(post.slug);
      assertEquals(found, post);

      found = posts.get(post2.slug);
      assertEquals(found, post2);
   }
   @Test
   public void testIndexOfNotFound()
   {
      posts.append(post.slug, post);
      posts.append(post2.slug, post2);
      assertEquals(2, posts.size());

      // posts should be => [ post, post2 ]

      assertEquals(-1, posts.indexOf("nf"));
   }
   @Test
   public void testIndexOf()
   {
      posts.append(post.slug, post);
      posts.append(post2.slug, post2);
      assertEquals(2, posts.size());

      // posts should be => [ post, post2 ]

      assertEquals(0, posts.indexOf(post.slug));
      assertEquals(1, posts.indexOf(post2.slug));
   }
   @Test
   public void testContainsNotFound()
   {
      posts.append(post.slug, post);
      assertFalse(posts.contains("nf"));
   }
   @Test
   public void testContainsFound()
   {
      posts.append(post.slug, post);
      assertTrue(posts.contains(post.slug));
   }
   @Test
   public void testListEmpty()
   {
      assertEquals(0, posts.list().size());
   }
   @Test
   public void testList()
   {
      posts.append(post.slug, post);
      assertEquals(1, posts.list().size());
      assertEquals(post, posts.list().get(0));
      assertNotSame(posts.list(), posts.list());

      posts.append(post2.slug, post2);
      assertEquals(2, posts.list().size());
      assertEquals(post, posts.list().get(0));
      assertEquals(post2, posts.list().get(1));
   }
   @Test
   public void testValuesEmpty()
   {
      assertEquals(0, posts.values().length);
   }
   @Test
   public void testValues()
   {
      posts.append(post.slug, post);
      assertEquals(1, posts.values().length);
      assertEquals(post, posts.values()[0]);
      assertNotSame(posts.values(), posts.values());

      posts.append(post2.slug, post2);
      assertEquals(2, posts.values().length);
      assertEquals(post, posts.values()[0]);
      assertEquals(post2, posts.values()[1]);
   }
   @Test
   public void testRemoveDoesNotExist()
   {
      assertNull(posts.remove("dne"));

      posts.append(post.slug, post);
      assertNull(posts.remove("dne"));
   }
   @Test
   public void testRemove()
   {
      posts.append(post.slug, post);
      posts.append(post2.slug, post2);
      posts.append(post3.slug, post3);
      assertEquals(3, posts.size());
      
      assertEquals(post, posts.get(0));
      assertEquals(post2, posts.get(1));
      assertEquals(post3, posts.get(2));

      Post removed = posts.remove(post.slug);

      assertEquals(post, removed);
      assertEquals(2, posts.size());

      assertEquals(post2, posts.get(0));
      assertEquals(post3, posts.get(1));

      try
      {
         posts.get(2);
         fail("should have thrown a IndexOutOfBoundsException");
      }
      catch(Exception ex)
      {
         assertTrue(ex instanceof IndexOutOfBoundsException);
      }

      removed = posts.remove(post3.slug);
      assertEquals(post3, removed);
      assertEquals(1, posts.size());

      assertEquals(post2, posts.get(0));

      try
      {
         posts.get(1);
         fail("should have thrown a IndexOutOfBoundsException");
      }
      catch(Exception ex)
      {
         assertTrue(ex instanceof IndexOutOfBoundsException);
      }

      removed = posts.remove(post2.slug);
      assertEquals(post2, removed);
      assertEquals(0, posts.size());
   }
   @Test
   public void testClearWhenEmpty()
   {
      assertEquals(0, posts.size());
      posts.clear();
      assertEquals(0, posts.size());
   }
   @Test
   public void testClear()
   {
      assertEquals(0, posts.size());
      posts.append(post.slug, post);
      posts.append(post2.slug, post2);
      assertEquals(2, posts.size());

      posts.clear();
      assertEquals(0, posts.size());
   }

   @Ignore
   public static class Post
   {
      public String slug;
      public String title;

      public Post(String slug, String title)
      {
         this.slug = slug;
         this.title = title;
      }

      @Override
      public boolean equals(Object obj)
      {
         return obj instanceof Post && ((Post) obj).slug.equals(slug) && ((Post) obj).title.equals(title);
      }
      @Override
      public String toString()
      {
         return Strings.format("[{0}] -> [{1}]", slug, title);
      }
   }
}