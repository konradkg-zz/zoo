package zoo.htmunit.tryouts;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.QueryOperators;

public class Mongo {
	
	public static void main(String[] args) {
		
		long t = TimeUnit.MINUTES.convert(1, TimeUnit.DAYS);
		System.out.println(t);
		t = t * 3;
		System.out.println(t);
		
		//int x = 2800000;
		int x = 284000;
		
		System.out.println(x/t);
		
		Query qDate = new Query();
		Criteria cdate = new Criteria("timestamp");
		cdate.lt(new Date());
		qDate.addCriteria(cdate);
		System.out.println(qDate);
		
		QueryBuilder bdate = QueryBuilder.start("timestamp_1");
		bdate.lessThan(new Date());
		System.out.println(bdate.get().toString());
		
		////
		Query q = new Query();
		//q.
		Criteria is_blacklisted = new Criteria("is_blacklisted");
		is_blacklisted.nin(true);
		
		Criteria array1 = new Criteria("array");
		array1.elemMatch(new Criteria("_id").is("999aaa666").and("status").nin("ok", "blacklisted"));
		
		Criteria array2 = new Criteria("array");
		array2.elemMatch(new Criteria("_id").is("999aaa666").and("status").is("queued").and("timestamp").gt(new Date()));
		
		Criteria array3 = new Criteria("array._id");
		array3.nin("999aaa666");
		
		
		Criteria or = new Criteria();
		or.orOperator(array1, array2, array3);
		
		
		q.addCriteria(is_blacklisted);
		q.addCriteria(or);
		
		System.out.println(q);
		
		
		
		
		QueryBuilder or1 = QueryBuilder.start("array").elemMatch(new BasicDBObject("a", 1).append("b", 2).append("status", new BasicDBObject(QueryOperators.NIN, new String[]{"ok", "done"})));
		QueryBuilder or2 = QueryBuilder.start("array").elemMatch(new BasicDBObject("a1", 1).append("b1", 2).append("c1", 3));
		QueryBuilder or3 = QueryBuilder.start("array").elemMatch(new BasicDBObject("a2", 1));
		
		BasicDBList l = new BasicDBList();
		l.add(new BasicDBObject("xxx", 11));
		
		QueryBuilder b = QueryBuilder.start("is_blacklisted");
		b.notIn(new Boolean[] {false});
		b.or(or1.get(), or2.get(), or3.get());
		
		
		
		//b.or(b.elemMatch(new BasicDBObject("a", 1).append("b", 2).append("c", 3)).get());
		//b.elemMatch(new BasicDBObject("a", 1).append("b", 2).append("c", 3));
		//b.elemMatch(new BasicDBObject("b", 2));
		//b.get();
		System.out.println(b.get().toString());
		
		b.toString();
		
	}

}
