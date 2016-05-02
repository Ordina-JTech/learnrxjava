package learnrxjava.exercises;

import java.util.Arrays;
import learnrxjava.types.JSON;
import learnrxjava.types.Movie;
import learnrxjava.types.Movies;
import rx.*;

import java.util.List;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import learnrxjava.examples.SubscribeOnObserveOnExample;
import learnrxjava.utils.Utils;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.observables.MathObservable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

/**
 * Now that you have mastered the ComposableList, it is time to move on.
 *
 * If you plunged directly into here, welcome!
 *
 * A summary of what you've skipped:
 * ComposableList introduces you gradually to functional composition and operators, using (and extending)
 * standard JDK collections.
 * Introduced operators are: forEach, map, filter, concatMap, reduce, zip. Some you implement yourself.
 * A few complex assignments require you to combine them wisely. Some exercises in here refer back to
 * ComposableList. In case you really need to, you may peek at ComposableListSolutions.
 *
 * Whatever path took you here; the exercises in this class will help you gain an understanding of Observables.
 * Before we go in depth about explaining what Observables are, first think of them as
 * ComposableLists or some other collection.
 * 
 * This means you can apply the same compositional methods (map, filter, etc) to 
 * Observables as to ComposableLists.
 *
 * An Observable is a lot like an Event as sent to the age-old JDK EventListener.
 * Like an Event, an Observable represents a sequence
 * of values that a data producer pushes to the consumer. However unlike an Event,
 * an Observable can signal to a listener that it has completed, and will send no more data.
 *
 * Observables can send data to consumers asynchronously. Observables are a sequence
 * of values, delivered one after the other. Therefore it's possible that an Observable
 * can go on sending data to a listener forever just like an infinite stream.
 *
 * Querying (pull-based) Lists only gives us a snapshot. By contrast, querying (push-based)
 * Observables allows us to create data sets that react and update as the system changes over time. This
 * enables a very powerful type of programming known as reactive programming.
 */
public class ObservableExercises {

    /**
     * We start with looking at the composability of Observables. This should feel
     * familiar if you've done the ComposableList exercises.
     */
    
    /**
     * Exercise 0 - mapping
     * <p/>
     * Transform the incoming Observable from just a list of names to a
     * friendly greeting, i.e. "Hello [Name]!".
     * <p/>
     * For example:
     * ["Remko", "Hedzer"] -> ["Hello Remko!", "Hello Hedzer!"]
     */
    public Observable<String> exercise00(Observable<String> names) {

        // ------------ ASSIGNMENT ----------------------------
        // Change the Strings in the names Observable using map.
        // Hint: You can use autocomplete.
        // ------------ ASSIGNMENT ----------------------------

        // return names. // TODO add implementation

        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 1 - filtering
     * <p/>
     * Given an observable of numbers, filter for even numbers:
     * <p/>
     * [1, 2, 3, 4, 5] -> [2, 4]
     */
    public Observable<Integer> exercise01(Observable<Integer> nums) {

        // ------------ ASSIGNMENT ----------------------------
        // Filter for even numbers        
        // ------------ ASSIGNMENT ----------------------------
        // return nums. // TODO add implementation

        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 2 - composition
     * <p/>
     * Just like with our ComposableList we can compose different functions
     * with Observables.
     * <p/>
     * Given an observable of numbers, filter for the even ones and transform them
     * to a String like the following:
     * <p/>
     * [1,2,3,4,5,6] -> ["2-Even", "4-Even", "6-Even"]
     */
    public Observable<String> exercise02(Observable<Integer> nums) {

        // ------------ ASSIGNMENT ----------------------------
        // Compose filter and map
        // ------------ ASSIGNMENT ----------------------------
        // return nums. // TODO add implementation

        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 3 - composing two operators
     * <p/>
     * Flatten out all videos in the stream of Movies into a stream of videoIDs.
     * Parameter movieLists effectively is an Observable of Observable of videoIDs.
     * Operator concatMap will sequentially concatenate the items emitted by the inner Observable,
     * resulting in a single 'layer' of Observable.
     * <p/>
     * Use the map operator to convert a Video to its id.
     * <p/>
     * @see ComposableListExercises#exercise11() - you can reuse your solution to that exercise here.
     * <p/>
     * @param movieLists
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exercise03(Observable<Movies> movieLists) {
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Introducing callbacks. 
     * 
     * Observable takes its name from the Observer pattern. The Observer observes
     * the Observable. For the Observable to notify the Observer, the Observer
     * will have to register itself with the Observable. The Observable can then
     * call(back) each Observer when it produced something. This should sound familiar
     * if you ever worked with for example something like Swing and Event Listeners.
     * 
     * In the following exercises we dive into this 'callback' principle. Note that
     * everything still happens synchronously. We encourage you to try this out 
     * by adding some printlns in your code and the test code. Using callbacks 
     * are the first step however to opening the door for asynchronicity. And it's
     * the asynchronous stuff where we will see the powers of the Observable.
     */
    
    /**
     * Exercise 4 - Observable basics
     * <p/>
     * When using an Iterable (like a normal List) we (the consumer) have to pull
     * the values out of the producer (the List). However, Observables are push
     * based, which turns things around. Now the producer (the Observable) chooses
     * the moment to push a value to us (the consumer) and we have to react to that
     * event, i.e. the reception of a new value.
     * <p/>
     * We specify how to react to such an event by supplying the Observable with
     * a subscription, by invoking Observable.subscribe(). On this subscription we
     * give the Observable a handle ('callback') to use when it has another value,
     * by passing in an instance of Subscriber (encapsulated in OnNext&lt;Integer&gt; here, for your
     * convenience).
     * <p/>
     * In the next exercise we will try to get a first look and feel for how this works.
     * The nums Observable will "push" (or "emit") values and you have add these values
     * together thus producing their count.
     * <p/>
     * For example:
     * [1,2,3,4,5,6] -> 21
     */
    public int exercise04(Observable<Integer> nums) {
        Sum sum = new Sum();

        // Here we subscribe to the Observable with our specific subscription. In our
        // subscription we can specify how to react to produced / pushed / emitted values.
        nums.subscribe(new OnNext<Integer>() {

            @Override
            public void onNext(Integer t) {
                // ------------ ASSIGNMENT ----------------------------
                // Update sum with the running total
                // Use auto complete
                // ------------ ASSIGNMENT ----------------------------
                // TODO add implementation
            }

        });

        //return sum.getSum();
        throw new UnsupportedOperationException("Not Implemented");

        // Sidenote: the pattern used here with adding values to an object outside the scope of
        // the onNext() is solely for educational purposes. We wouldn't advise using it, i.e.
        // not without using an appropriate concurrent structure. You may ponder this or just
        // wait until the section about Schedulers.
    }

    /**
     * Exercise 5 - error
     * <p/>
     * The previous exercise should have looked familiar. In fact it is the standard
     * Observable pattern as described by the Gang of Four [GoF]. However, this
     * standard pattern misses two important concepts. Two concepts that are present on
     * an Iterable.
     * <p/>
     * Let's revisit Iterable. As we saw Iterable (by means of iterator) provides us with
     * a couple of scenarios:
     * <p/>
     * next()    - to get the next element,
     * hasNext() - to check if there are more elements and
     * it can throw an exception if anything is wrong.
     * <p/>
     * Thus far we've only seen the next() equivalent for Observables (onNext()), but we
     * still lack two.
     * <p/>
     * First we will implement the onError() method, which is called if the observable
     * throws an exception.
     */
    public String exercise05(Observable<Integer> faultyNums) {
        StringBuilder message = new StringBuilder();

        // Faulty nums is an Observable that will throw an exception
        faultyNums.subscribe(new OnError<Integer>() {

            @Override
            public void onError(Throwable t) {
                // ------------ ASSIGNMENT ----------------------------
                // Extract the error message and return it
                // ------------ ASSIGNMENT ----------------------------
                // TODO add implementation

                throw new UnsupportedOperationException("Not Implemented");
            }

        });

        //return message.toString();
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * Exercise 6 - completion
     * <p/>
     * Let's complete (pun intended!) our survey of the three Observable interface methods
     * by using onCompleted() to aggregate the result of onNext()'ing through a stream
     * of numbers.
     *
     * onComplete() can also be used to free any resources, if and when required.
     */
    public String exercise06(Observable<Integer> someNumbers) {
        StringBuilder message = new StringBuilder();

        someNumbers.subscribe(new OnComplete<Integer>() {
            int count = 0;
            @Override
            public void onNext(Integer t) {
                // ------------ ASSIGNMENT ----------------------------
                // Increment count by one
                // ------------ ASSIGNMENT ----------------------------
                // TODO add implementation
            }

            @Override
            public void onCompleted() {
                // ------------ ASSIGNMENT ----------------------------
                // Set the message to "found <count> items"
                // ------------ ASSIGNMENT ----------------------------
                // TODO add implementation
            }

        });

        //return message.toString();
        throw new UnsupportedOperationException("Not Implemented");
    }

    /**
     * Exercise 7 - production
     * <p/>
     * Now we'll explore the producer side of the push mechanism.
     * <p/>
     * An Observable produces values; an Observer, with its sub-interface Subscriber represents the consumer side
     * of things.
     * <p/>
     * The previous three exercises focused on the consumer side, by accepting onNext, onComplete and onError events.
     * Here, we will _produce_ these events. The most basic producer creates an Observable that emits just a single value.
     * After emitting that value, the subscriber must be informed that the stream is complete.
     * @param name value to emit
     * @return
     */
    public Observable<String> exercise07(String name) {
        return Observable.create(subscriber -> {
            // ------------ ASSIGNMENT ----------------------------
            // Signal 2 events to the subscriber that has been handed to us by Observable.create().
            // The first event must emit the name, the second must signal that
            // the emission of the name was the last event and no more are coming.
            // ------------ ASSIGNMENT ----------------------------
            // TODO add implementation
            throw new UnsupportedOperationException("Not Implemented");
        });
    }

    /**
     * Exercise 8 - wrapping up the basics
     * <p/>
     * Now we'll add error propagation to the mix. A producer can emit an error - in the form of a Throwable -
     * to signal an exceptional situation. The enables the consumer to take corrective action.
     * <p/>
     * @param divisor will divide the number 42
     * @return Observable that emits a single value, either "The number 42 divided by your input is: <number>"
     *         or an error.
     */
    public Observable<String> exercise08(int divisor) {
        return Observable.create(subscriber -> {
            try {
                int quotient = 42 / divisor;
                // ------------ ASSIGNMENT ----------------------------
                // Emit just the value "The number 42 divided by your input is: <quotient>"
                // ------------ ASSIGNMENT ----------------------------
                // TODO add implementation
                throw new UnsupportedOperationException("Not Implemented");
            } catch (Exception e) {
                // ------------ ASSIGNMENT ----------------------------
                // Emit - not throw! - the Exception. Can you think of a reason
                // why it would be unwise to rethrow here?
                // ------------ ASSIGNMENT ----------------------------
                // TODO add implementation
                throw new UnsupportedOperationException("Not Implemented");
            }
        });
    }

    /**
     * A short tour of the API.
     * 
     * Before we dive into timing, we will build some API knowledge. This should
     * be useful later on. If you're anxious and don't mind learning the API on
     * the go you could skip to exercise 18.
     */
    
    /**
     * Exercise 9 - just do it
     * <p/>
     * RxJava actually has an operator to do that which you have just (!) programmed in exercises 07 and 08.
     * <p/>
     * Its name is - just as you expected - Observable.just().
     * <p/>
     * Contrary to what you'd expect with this name, it's actually also possible to have it emit _multiple_
     * values, by supplying up to 9 arguments.
     */
    public Observable<String> exercise09() {
        // ------------ ASSIGNMENT ----------------------------
        // emit a single value "Hello World!"
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 10 - zip it up
     * <p/>
     * @see ComposableListExercises#exercise22() for your first encounter with zip.
     * Here, we will use it to simply combine 2 streams of Strings into pairs using zip.
     * <p/>
     * Example data appearing in the input streams:
     * a -> "one", "two", "red", "blue"
     * b -> "fish", "fish", "fish", "fish", "fish", "fish", "fish", "fish"
     * output to be produced -> "one fish", "two fish", "red fish", "blue fish"
     * <p/>
     * Note that any items without a counterpart in the sibling input stream will be dropped - the number of items in the
     * output will be equal to _the minimum_ of the number of items in both of the input streams.
     */
    public Observable<String> exercise10(Observable<String> a, Observable<String> b) {
        // ------------ ASSIGNMENT ----------------------------
        // zip up Observable a and b using a combiner function that concatenates the input from both values
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 11 - First come, first served
     * <p/>
     * Observable comes with a lot of nifty convenience methods. first() is one of
     * them. It can help you when you don't feel the need to look further.
     * <p/>
     * So don't look further and finish the exercise. Just for fun, we threw in
     * a little concatMap as well...
     * 
     * @param movieLists an observable of lists of movies to work your magic on
     * @return the title of the first video of the first list of movies
     */
    public Observable<String> exercise11(Observable<Movies> movieLists) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.first(), you might need some concatMap too...
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 12 - The 'last' exercise
     * <p/>
     * This time we are looking for the title of the last movie that the 
     * Observable emits that matches a specific rating
     * <p/>
     *
     * @param movieLists an observable of movies to work your magic on
     * @return the title of the latest movie that matches a rating
     * 
     * PS: Comparing doubles with ==??? Yuck. We know. Not proud of it but for 
     *     these exercises it works :)
     */
    public Observable<String> exercise12(Observable<Movies> movieLists, double rating) {
        // ------------ ASSIGNMENT ----------------------------
        // Get the last movie title that the Observable emits that matches the given rating.
        // Use Observable.last()
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 13 - Take it or leave it
     * <p/>
     * Return an Observable that returns the first 5 movies
     * <p/>
     *
     * @param movieLists an observable of lists of movies to work your magic on
     * @return the titles of the first 5 movies
     */
    public Observable<String> exercise13(Observable<Movies> movieLists) {
        // ------------ ASSIGNMENT ----------------------------
        // Get the first 5 movie titles
        // Use Observable.take()
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 14 - Don't skip this one
     * <p/>
     * Return an observable that returns the titles of the movies on the second page
     * Use pageLength to get the movies for the second page
     * <p/>
     *
     * @param movieLists an observable of lists of movies to work your magic on
     * @param pageLength the number of items on a page
     * @return the movies on the second page
     */
    public Observable<String> exercise14(Observable<Movies> movieLists, int pageLength) {
        // ------------ ASSIGNMENT ----------------------------
        // Skip the movies on the first page and return the titles of the movies 
        // that are on the second page. Use Observable.skip()
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 15 - No limit, Take 5
     * <p/>
     * Return an observable that only emits the best 5 movies or less.
     * <p/>
     *
     * @param movieLists an observable of movies to work your magic on
     * @return all titles of movies with a rating equal or higher than 4.5 and give 5 movies or less
     */
    public Observable<String> exercise15(Observable<Movies> movieLists) {
        // ------------ ASSIGNMENT ----------------------------
        // Return all the titles of the movies that are equal to or higher than 4.5 in rating. 
        // Also just give me 5 good movies or less. Use Observable.limit().
        // When done, try Observable.take() & find out the difference.
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 16 - Are there epic movies?
     * <p/>
     * Return an Observable that emits, for each movie, whether there are any epic interestingMoments
     * <p/>
     *
     * @param movieLists an observable of movies to work your magic on
     * @return An Observable that emits true if a movie has an epic interestingMoment
     */
    public Observable<Boolean> exercise16(Observable<Movies> movieLists) {
        // ------------ ASSIGNMENT ----------------------------
        // Return true if there is an epic (interestingMoment.type.equals("epic")) movie.
        // Use Observable.exists();
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 17 - Rated R.
     * <p/>
     * Return true if all movies in the marathonCandidates are suitable for non adults.
     * <p/>
     *
     * @param marathonCandidates an observable of movies to work your magic on
     * @return An Observable that emits true if all movies are suitable for someone under 18 years
     *         of age, false otherwise
     */
    public Observable<Boolean> exercise17(Observable<Movies> marathonCandidates) {
        // ------------ ASSIGNMENT ----------------------------
        // Return true if all movies have a minimalAge lower than 18
        // Use Observable.all();
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * The time has come to learn about timing. This is the point of no return*.
     * Dealing with timing issues is where we will start to see Rx shine. The
     * combination of the callback structure, rich API and functional composability
     * allow for elegant solutions to some otherwise more complex problems.
     * 
     * * = Backward time traveling excluded
     */
    
    /**
     * Exercise 18 - Timing is everything
     * <p/>
     * Now that we're familiar with just and zip, we can begin to add a touch of timing.
     * We can exploit the fact that zip requires both values to be present at the same time - and thus
     * has to wait until the last of each pair has arrived - to slow down a fast-paced stream. Zipping that
     * with the interval Observable will do just that.
     * <p/>
     * @return an Observable with items "one 1", "two 2", etc., each 1 second apart
     */
    public Observable<String> exercise18(Scheduler scheduler) {
        Observable<String> data = Observable.just("one", "two", "three", "four", "five");

        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.interval to get an item emitted each second.
        // Use the overload of interval that takes a scheduler and pass
        // the provided scheduler. Don't break your head on this one,
        // more about schedulers will follow in time.
        // Use Observable.zip (static) or Observable.zipWith (instance)
        // to achieve the desired output
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
        
        // Test slow? Did you forget to add the scheduler?
    }

    /**
     * Exercise 19 - Merge hell
     * <p/>
     * Timing matters when choosing which method to use. Merging is an operation
     * that preserves timing, as you will see below. Go ahead and take a look at
     * the corresponding unit test as well.
     * 
     * @param odd  an observable that emits an odd number every odd second
     * @param even an observable that emits an even number every even second
     * @return an Observable with the results
     */
    public Observable<Long> exercise19(Observable<Long> odd, Observable<Long> even) {
        // ------------ ASSIGNMENT ----------------------------
        // use Observable.mergeWith to interleave the two streams
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 20 - flatMap vs. concatMap; ordering and concurrency
     * <p/>
     * Let's revisit the nice domain of videos, of which we temporarily strayed
     * in the dull non-domain-oriented exercises above.
     * <p/>
     * Flatten out all video in the stream of Movies into a stream of videoIDs.
     * <p/>
     * Use flatMap this time instead of concatMap. In Observable streams
     * it is almost always flatMap that is wanted, not concatMap as flatMap
     * uses merge instead of concat and allows multiple concurrent streams
     * whereas concat only does one at a time.
     * <p/>
     * We'll see more about this later when we add concurrency.
     *
     * @param movieLists
     * @return Observable of Integers of Movies.videos.id
     */
    public Observable<Integer> exercise20(Observable<Movies> movieLists) {
        // ------------ ASSIGNMENT ----------------------------
        // Use flatMap
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 21 - RTFM!
     * <p/>
     * Let's read up on marble diagrams. Marble diagrams depict data flows for each of the reactive Observables.
     * They help enormously in understanding what's going on.
     * <p/>
     * For your convenience, they're included in the RxJava javadoc. You can revisit
     * your solutions to two previous exercises, and lookup the javadoc in your IDE.
     * <p/>
     * @see ObservableExercises#exercise03(Observable) - here you used concatMap
     * @see ObservableExercises#exercise20(Observable) - here you used flatMap
     * <p/>
     * Look carefully at both diagrams. Where do they differ? Can you relate this to section 3 in
     * the description in exercise 20?
     * If and when you understand, please say so in the code below, and progress to the next exercise.
     * <p/>
     * For more info, please visit:
     * <ul>
     *     <li>https://github.com/ReactiveX/RxJava/wiki</li>
     *     <li>http://reactivex.io/documentation/operators/flatmap.html</li>
     * </ul>
     * @return true when you understand what's going on
     */
    public boolean exercise21() {
        return false;
    }

    /**
     * Exercise 22 - A sample of a fine exercise
     * <p/>
     * Lazy as we are, we are not going to continuously watch a possibly infinite stream.
     * That would like watching the output of your compiler. And who does that?
     * Much better, we are going to take sample now and then and look at that.
     * <p/>
     * For this exercise, we are going to sample every four seconds. 
     * 
     * @see http://reactivex.io/RxJava/javadoc/rx/Observable.html#sample(long, java.util.concurrent.TimeUnit)
     * @param movieLists an observable of lists of movies that will spit out something every three seconds
     * @param scheduler the scheduler you should use for sampling
     * @return the titles of the movies at the point of sampling (after four seconds)
     */
    public Observable<String> exercise22(Observable<Movies> movieLists, Scheduler scheduler) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.sample() and the provided scheduler.
        // Don't worry about schedulers yet. In time you will learn.
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 23 - buffer that burst, or the bubble will burst!
     * <p/>
     * Picture this: an external service pushes ids of suggested video's to watch.
     * Being an external service on a network, its traffic can be 'bursty': the one moment a lot of data,
     * the other hardly anything. The bursts might swamp us.
     * <p/>
     * The buffer operator partially insulates us from these bursts, by buffering them up and emitting the
     * buffers as a List of items. On what conditions these Lists are emitted is entirely configurable,
     * by way of 12 different overloads of Observable.buffer()!
     * <p/>
     * The objective: buffer the incoming burstySuggestedVideoIds in intervals of 500ms.
     * <p/>
     * Your task: look up the buffer operator at http://reactivex.io/documentation/operators/buffer.html.
     * Click through to the RxJava Language-Specific Information, and find the required variant.
     * <p/>
     * Note !! You must invoke buffer() on the specified Scheduler, otherwise the unit test will not pass !!
     * More about scheduling & testing will be revealed in exercise 30.
     *
     * @param burstySuggestedVideoIds an Observable that emits a lot of video id's
     * @return buffered Lists of suggestedVideoIds at 500ms intervals
     */
    public Observable<List<Integer>> exercise23(Observable<Integer> burstySuggestedVideoIds, Scheduler scheduler) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.buffer()
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 24 - Windows
     *
     * This time we have the same service but instead of buffering the video id's to a list we want to buffer the video id's to an Observable.
     * In order to do this we can make use of the Observable.window() method.
     *
     * Have a look at http://reactivex.io/documentation/operators/window.html to see the difference between buffer and window.
     *
     * @param burstySuggestedVideoIds an Observable that emits a lot of video id's
     * @return video id's, in windows of 200 milliseconds, one window every 1000 milliseconds
     */
    public Observable<Observable<Integer>> exercise24(Observable<Integer> burstySuggestedVideoIds, Scheduler scheduler) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.window()
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 25 - no pay? delay!
     * <p/>
     * In this exercise, we will give a free-loading movie watcher the opportunity to watch each movie for 10 seconds,
     * then show one or more adverts in parallel, then the next movie for 10s, then some ads, et cetera.
     * <p/>
     * Your assignment is to map the advertFunction on the movies the user selected, and then flatMap to apply a 10sec
     * delay to each ad.
     * <p/>
     * The result should be a delayed stream of advert movies. It's assumed the actual movies are displayed/merged in the caller
     * of your function - you only have to worry about getting ads out, 10 secs after receiving the movie.
     * <p/>
     * Note that Observable.delay() has four variants. We use the simplest variant: timeshift all items at once.
     * This is why you need to delay _inside_ flatMap - otherwise the user will only see 1 video for 10s and then all ads.
     *
     * @param movieLists movies user wants to view
     * @param advertFunction function that generates adverts based on the selected movie
     * @return advert movies, 10s apart
     */
    public Observable<Movie> exercise25(Observable<Movie> movies, Func1<Movie, Observable<Movie>> advertFunction, Scheduler scheduler) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.delay() (and map and flatMap)
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 26 - Throttling!
     * <p/>
     * By now you should be familiar with the Observable.sample(). (Exercise 22)
     * With the throttling methods of Observable you have more options in how to take a _sample_ of a stream.
     * <p/>
     * Let's sample this stream of movies every 200 milliseconds and figure out what the average rating is.
     * MathObservable will be your friend :)
     * <p/>
     * As with the previous exercise, don't forget to hand Scheduler to the throttling operator. You wouldn't want to use
     * wall clock timing in a unit test, would you?
     *
     * @param movieLists an Observable of movies to work your your magic on
     * @return The average rating of the _throttled_ movies
     */
    public Observable<Double> exercise26(Observable<Movie> movieLists, Scheduler scheduler) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.throttleLast()
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
         return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 27 & 28 - Hot and Cold Observables.
     * 
     * The tables have turned. Instead of writing the implementation, you will
     * have to complete the test code. You will grow faster when you look at 
     * things from a different perspective now and then. 
     * 
     * Your journey continues in ObservableExercisesTest, line 364. Good luck! If you work
     * diligently and with purpose in your heart, we will meet again here.
     * 
     * Go now!
     */
    public void exercise27(
            Observable<Long> nums, 
            TestScheduler scheduler, 
            TestSubscriber immediateSubscriber,
            TestSubscriber delayedSubscriber) {
        
        nums.subscribe(immediateSubscriber);
        scheduler.advanceTimeBy(5, SECONDS);
        nums.subscribe(delayedSubscriber);
    } 
        
    /** 
     * Exercises 27 - 33 reside in ObservableExercisesTest. Of course you'd 
     * known this if you read the instructions for exercise 27. And you wouldn't
     * skip ahead without our permission, right?
     */

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Welcome back! 
     * 
     * By now we've transformed, composed, traveled in time and and even walked 
     * different threads. All in the name of Reactive Programming. There's still
     * something missing though. Can you guess what it is?
     * 
     * Correct! What's reactive programming without some resilience. We might be
     * non-blocking now, but that alone doesn't prevent failure. In a real system
     * nothing will. That's why we better prepare for the worst by adding some
     * resilience.
     * 
     * Finally, this is one of those few lucky times where you can throw errors
     * all over the place and nobody will get upset.
     */
    
    /**
     * Exercise 34 - Fallback
     *
     * We start of easy. When an Observable emits an error, the stream is stopped
     * in the normal flow. We can however provide a default fallback value that is
     * to emitted should such a situation occur. The stream is terminated after
     * that.
     * @param faultyObservable
     */
    public Observable<Integer> exercise34(Observable<Integer> faultyObservable) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.onErrorReturn()
        // Return -1 as default value
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 35 - I love it when a plan fails but it still comes together in another plan
     *
     * @param planA Will probably fail
     * @param planB To rescue us when plan A fails
     */
    public Observable<Integer> exercise35(Observable<Integer> planA, Observable<Integer> planB) {
        // ------------ ASSIGNMENT ----------------------------
        // Resume from an error in observable planA by switching to planB
        // No hint this time. Look in the API for the right method
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }
    
    /**
     * Exercise 36 - Third time's a charm
     *
     * The supplied data stream (supplied by the unit test) fails intermittently.
     * Your task is to return the stream, augmented with retry capability.
     *
     * @param intermittentStream a stream of data that fails intermittently
     */
    public Observable<String> exercise36(Observable<String> intermittentStream) {
        // ------------ ASSIGNMENT ----------------------------
        // use retry()
        // Hint: just retry without parameters is not enough...
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**********************************************************************************
     * My friend, you've neared the end. Following are some extra exercises, should
     * your hunger have not laid down by now. After that, you will have gained enough
     * knowledge to lay down your own path.
     **********************************************************************************/

    /**
     * Exercise 37 - The inevitable Group By
     * 
     * This exercise is more advanced. Don't worry if it takes some time to wrap your head around it.
     * 
     * @param movieLists an observable of movies to work your magic on
     * @return the ratings of all the movies made per actor
     */
    public Observable<GroupedObservable<String, Double>> exercise37(Observable<Movies> movieLists) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable.groupBy()
        // Hint1: You can find the actors in the topCast property of a Movie
        // Hint2: You can use AbstractMap.SimpleEntry<K, V> to store the relation between an actor and its movies
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 38 - Reductio ad absurdum
     *
     * Retrieve the largest number.
     * <p/>
     * Use reduce to select the maximum value in a list of numbers.
     * <p/>
     * Reduction works iteratively, processing each item one by one, applying it to the result
     * of the previous step. The process is started by combining the first two emitted items.
     * <p/>
     * The combiner function is named 'accumulator'.
     * <p/>
     * Only the _last_ result of invoking the accumulated is emitted, so reduce() emits (at most) one item.
     */
    public Observable<Integer> exercise38(Observable<Integer> nums) {
        // ------------ ASSIGNMENT ----------------------------
        // use reduce() to find the maximum number
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 39 - Scan
     *
     * Scan works just like reduce, with the distinction that it emits all intermediate results.
     * The number of emitted results equals the number emitted by the Observable being scanned.
     * 
     * Your assignment is the same as in the previous exercise, but this time use scan to find
     * the maximum number (and all intermediate results)
     */
    public Observable<Integer> exercise39(Observable<Integer> nums) {
        // ------------ ASSIGNMENT ----------------------------
        // use scan()
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
        
        // Do you understand the subtle difference between reduce and scan? Looking
        // at the tests could provide some extra insights.
    }

    /**
     * Exercise 40 - reduce, composed
     *
     * Retrieve the id, title, and <b>smallest</b> box art url for every video.
     * <p/>
     * Now let's try combining reduce() with our other functions to build more complex queries.
     * <p/>
     * This is a variation of the problem we solved earlier, where we retrieved the url of the boxart with a
     * width of 150px. This time we'll use reduce() instead of filter() to retrieve the _smallest_ box art in
     * the boxarts list.
     * <p/>
     * @see ComposableListExercises#exercise13() - ComposableListExercises#exercise19()
     */
    public Observable<JSON> exercise40(Observable<Movies> movies) {
        // ------------ ASSIGNMENT ----------------------------
        // use reduce() - see ComposableListSolutions#exercise19() for more hints
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 41 - Sort it out 
     *
     * Sorting is a simple operation. Keep in mind that sorting requires buffering _all_ items - the last one may very well
     * be the smallest. This is reflected by the fact that a _List_ of the input type is returned.
     *
     * The sort buffer may require quite some memory when streaming a large number of items, so be careful!
     */
    public Observable<List<String>> exercise41(Observable<String> data) {
        // ------------ ASSIGNMENT ----------------------------
        // Use Observable's 'toSortedList' operator to sort lexicographically
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 42 - Sort it out by length
     *
     * Sorting is by default done by using the Comparable implementation of the items themselves.
     *
     * In this exercise, you will build an external sorter. Should be simple enough: supply the comparison as a lambda expression.
     */
    public Observable<List<String>> exercise42(Observable<String> data) {
        // ------------ ASSIGNMENT ----------------------------
        // Use an overload of Observable's 'toSortedList' operator, supplying a sortFunction
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 43 - A distinctive exercise
     *
     * One way of protecting against overload - e.g. if the stream needs to be sorted - is to filter out items from the stream.
     *
     * Of course, you'll have to decide, based on your specific domain, in which cases 'forgetting' items
     * from the stream is desirable.
     *
     * Filtering can be done in any number of ways. Examples are:
     * - sample(time): return one item per time interval
     * - distinct: remove duplicates
     * - first: take _only_ the first item
     * - takeWhile(condition): take while 'condition' is true
     * - skip(n), skip(time): skip n items, or skip until the time interval has passed
     * - take(n), take(time), takeLast(n), takeLast(time): take the first or last n items
     * - take(time), takeLast(time): take the first or last items in the specified time interval
     *
     * ... and many others, like skip(time), last, first(condition), firstOrDefault(default),
     * lastOrDefault(default), elementAt(x), elementAtOrDefault(x, default), skipLast(n), distinctUntilChanged, etc.
     *
     * But, we're getting ahead of ourselves. That's a pretty hefty list of operators, which we can't possibly
     * all cover in this workshop.
     *
     * You've seen skip() before. This time we will introduce you to another 'pruning' operator: distinct().
     */
    public Observable<String> exercise43(Observable<String> movieIds) {
        // ------------ ASSIGNMENT ----------------------------
        // Skip the first 3 movies. Of the remaining movies, return only the movies that are different from
        // what has already been emitted
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        return Observable.error(new RuntimeException("Not Implemented"));
    }

    /**
     * Exercise 44 - (de)materialize
     *
     * In some cases, it's useful to be able to make the 3 event types (element, error or complete)
     * explicit. This is named 'materialization'. The reverse is 'dematerialization'. Effectively, this (un)wraps
     * events with a Notification, which has an enum Kind property with values OnNext, OnError and OnCompleted.
     * <p/>
     * Materializing an Observable enables you to switch on the type, instead of supplying a closure for each
     * event.
     * <p/>
     * Your assignment: complete the given code to make it equivalent to: map(i -> i + 1)
     * <p/>
     * As you can see, materializing adds a lot of boilerplate. Use it only when required for complicated use cases - see the next exercise.
     * <p/>
     * Note the use of flatMap to concatenate the individual single-event Observables returned by the switch-construction.
     * @param videoIds an Observable of videoIds
     * @return Observable of videoIds, increased by one
     */
    public Observable<Integer> exercise44(Observable<Integer> videoIds) {
        return videoIds.materialize().flatMap((Notification<Integer> notification) -> {
                    switch (notification.getKind()) {
                        case OnNext:
                            // ------------ ASSIGNMENT ----------------------------
                            // use Notification.createOnNext()
                            // ------------ ASSIGNMENT ----------------------------
                            // TODO add implementation
                            return Observable.error(new RuntimeException("Not Implemented"));
                        case OnError:
                            return Observable.error(notification.getThrowable());
                        case OnCompleted:
                        default:
                            return Observable.just(notification);
                    }
                }
        ).dematerialize();
    }

    /**
     * Exercise 45 - retry and (de)materialize, composed
     *
     * A 'reading' exercise. Its drives the point home of using materialize() only when really needed.
     * The focal point, however, is the retry() operator. The conditionalRetry() method 'lifts'
     * it to (infinitely) retry only *recoverable* errors.
     *
     * This feature is illustrated by simply concatenating the results of an (eventually) succeeding Observable, and one
     * that fails with an unrecoverable IllegalStateException.
     *
     * There is no code to be added for this 'exercise'. Simply read, execute & learn.
     */
    public Observable<String> exercise45(String name) {
        Observable<String> oWithRuntimeException = failingObservable(3, s -> {
            s.onNext(name);
            s.onCompleted();
        });
        Observable<String> oWithIllegalStateException = failingObservable(3, s -> s.onError(new IllegalStateException()));

        Observable<String> helloObservable = conditionalRetry(oWithRuntimeException, t -> t instanceof IllegalStateException);
        Observable<String> failingObservable = conditionalRetry(oWithIllegalStateException, t -> t instanceof IllegalStateException);

        return helloObservable.concatWith(failingObservable);
    }

    /**
     * This is a helper method for exercise 45.
     *
     * Creates an Observable that will fail the first 'fails' times with a RuntimeException.
     * This simulates a recoverable error.
     * Finally, the supplied action will be invoked, passing in the Subscriber.
     *
     * @param fails number of simulated failures
     * @param action subscription action
     * @param <T> type of emitted items
     * @return recoverable Observable
     */
    protected  <T> Observable<T> failingObservable(int fails, Observable.OnSubscribe<T> action) {
        final AtomicInteger c = new AtomicInteger();
        return Observable.create((Subscriber<? super T> s) -> {
//            System.out.println("Try: " + c.get());
            if (c.incrementAndGet() < fails) {
                s.onError(new RuntimeException("recoverable"));
            } else {
                action.call(s);
            }
        });
    }

    /**
     * This is a helper method for exercise 45.
     *
     * Modifies the supplied Observable to be conditionally retryable. Inifinite retries will be performed,
     * unless the error event matches the supplied isFatal condition.
     *
     * @param o Observable to make conditionally retryable
     * @param isFatal predicate to determine whether an error event is fatal
     * @return conditionally retryable Observable
     */
    protected  <T> Observable<T> conditionalRetry(final Observable<T> o, Predicate<Throwable> isFatal) {
        return o.materialize().flatMap(n -> {
            if (n.isOnError()) {
                if (isFatal.test(n.getThrowable())) {
                    /* this will emit the IllegalStateException during dematerialize();
                       effectively IllegalStateException won't be retried by retry() because
                       it's wrapped in a Notification of Kind.OnError.
                     */
                    return Observable.just(n);
                } else /* RuntimeException - let's 'rethrow' to let retry() kick in */ {
                    return Observable.error(n.getThrowable());
                }
            } else {
                return Observable.just(n);
            }
        }).retry().dematerialize();
    }

    /**
     * Exercise 46 - the intriguing subject of Subjects.
     * <p/>
     * A Subject is 'both an Observable and an Observer' (as per the javadoc).
     * This means you can observe it in the usual way, but also add new items to the stream.
     * So, items are _pushed to you_ (Observable) but you can also _push items in_ (Observer).
     * <p/>
     * Your task: supply your customer with an initial movie, and give them to option to subscribe
     * to a replay (!) of movies watched so far. New movies can be pushed in. Movies older than 5 hours
     * must be expunged.
     * <p/>
     * Use your knowledge built up so far to find the required Subject implementation and creation method.
     *
     * @param initialMovieName item to push in
     * @param scheduler our trusty unit test scheduler
     * @return a ReplaySubject
     */
    public Subject<String,String> exercise46(String initialMovieName, TestScheduler scheduler) {
        // ------------ ASSIGNMENT ----------------------------
        // use some implementation of rx.subjects.Subject
        // ------------ ASSIGNMENT ----------------------------
        // TODO add implementation
        throw new RuntimeException("Not Implemented");
    }

    /**
     * ****************************
     * Final notes
     * ****************************
     *
     * CONGRATULATIONS!
     *
     * Our guidance ends here. From here on you will have to follow your own path.
     *
     * Take a look at the examples in the learnrxjava.examples package, study the API, read about reactive streams
     * and backpressure or try to build your own reactive application.
     * 
     * We hope you enjoyed the ride and emerged enlightened. If you're interested and want to know more,
     * don't hesitate and contact us.
     * 
     * Regards,
     * 
     * Hedzer Westra <hedzer.westra@ordina.nl>
     * Remko de Jong <remko.de.jong@ordina.nl>
     */
    
    /**
     * ****************************
     * References & Further reading
     * ****************************
     * 
     * Code repository for RxJava
     * https://github.com/ReactiveX/RxJava
     * 
     * Javadoc for RxJava
     * http://reactivex.io/RxJava/javadoc/
     *
     * Official Reactive Streams website
     * http://www.reactive-streams.org/
     * 
     * HTML5DevConf Jafar Husain, Netflix: Asyncronous JavaScript at Netflix
     * https://www.youtube.com/watch?v=5uxSu-F5Kj0
     *
     * Functional Reactive Programming with RxJava by Ben Christensen
     * https://www.youtube.com/watch?v=_t06LRX0DV0
     *
     * Functional Reactive Programming with RxJava
     * https://www.youtube.com/watch?v=Dk8cR1Kxj0Y
     *
     * Other
     * Google has all the answers my friend. If you're paranoid DuckDuckGo will help you too :)
     */
    
    /*
     * ****************************************************************
     * below are helper methods, you are free to look at them of course
     * ****************************************************************
     */

    // This function can be used to build JSON objects within an expression
    public static JSON json(Object... keyOrValue) {
        JSON json = new JSON();

        for (int counter = 0; counter < keyOrValue.length; counter += 2) {
            json.put((String) keyOrValue[counter], keyOrValue[counter + 1]);
        }

        return json;
    }

    public static abstract class OnNext<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            // NOOP
        }

        @Override
        public void onError(Throwable e) {
            // NOOP
        }
    }

    public static abstract class OnComplete<T> extends Subscriber<T> {
        @Override
        public void onNext(T t) {
            // NOOP
        }

        @Override
        public void onError(Throwable e) {
            // NOOP
        }
    }

    public static abstract class OnError<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            // NOOP
        }

        @Override
        public void onNext(T t) {
            // NOOP
        }
    }

    public static class Sum {
        private int sum;

        public void increment(int withAmount) {
            sum += withAmount;
        }

        public int getSum() {
            return sum;
        }
    }
    
}
