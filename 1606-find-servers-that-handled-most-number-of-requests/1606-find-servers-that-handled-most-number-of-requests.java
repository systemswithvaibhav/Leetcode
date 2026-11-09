import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> available = new TreeSet<>();
        for (int server = 0; server < k; server++) {
            available.add(server);
        }

        // Each entry is {finishTime, serverIndex}
        PriorityQueue<int[]> busy =
            new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        int[] handled = new int[k];

        for (int i = 0; i < arrival.length; i++) {
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                available.add(busy.poll()[1]);
            }

            if (available.isEmpty()) {
                continue; // All servers are busy; drop this request.
            }

            int preferred = i % k;
            Integer server = available.ceiling(preferred);
            if (server == null) {
                server = available.first(); // Wrap around.
            }

            available.remove(server);
            handled[server]++;
            busy.offer(new int[]{arrival[i] + load[i], server});
        }

        int max = 0;
        for (int count : handled) {
            max = Math.max(max, count);
        }

        List<Integer> result = new ArrayList<>();
        for (int server = 0; server < k; server++) {
            if (handled[server] == max) {
                result.add(server);
            }
        }
        return result;
    }
}