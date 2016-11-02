

 

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import java.util.Scanner;

 

public class Graph{

    static int lca(int[] mch, int[] bs, int[] p, int a, int b)

    {

        boolean[] selected = new boolean[mch.length];

        while (true)

        {

            a = bs[a];

            selected[a] = true;

            if (mch[a] == -1)

                break;

            a = p[mch[a]];

        }

        while (true)

        {

            b = bs[b];

            if (selected[b])

                return b;

            b = p[mch[b]];

        }

    }

 

    static void selectpath(int[] mch, int[] bs, boolean[] blsm, int[] p, int v, int b, int chld)

    {

        for (; bs[v] != b; v = p[mch[v]])

        {

            blsm[bs[v]] = blsm[bs[mch[v]]] = true;

            p[v] = chld;

            chld = mch[v];

        }

    }

 

    static int findPath(List<Integer>[] grph, int[] mch, int[] p, int rt) {

        int n = grph.length;

        boolean[] selected = new boolean[n];

        Arrays.fill(p, -1);

        int[] bs = new int[n];

        for (int i = 0; i < n; ++i)

            bs[i] = i;

        selected[rt] = true;

        int qh = 0;

        int qt = 0;

        int[] q = new int[n];

        q[qt++] = rt;

        while (qh < qt)

        {

            int v = q[qh++];

            for (int to : grph[v]){

                if (bs[v] == bs[to] || mch[v] == to)

                    continue;

                if (to == rt || mch[to] != -1 && p[mch[to]] != -1)

                {

                    int curbase = lca(mch, bs, p, v, to);

                    boolean[] blsm = new boolean[n];

                    selectpath(mch, bs, blsm, p, v, curbase, to);

                    selectpath(mch, bs, blsm, p, to, curbase, v);

                    for (int i = 0; i < n; ++i)

                        if (blsm[bs[i]])

                        {

                            bs[i] = curbase;

                            if (!selected[i])

                            {

                                selected[i] = true;

                                q[qt++] = i;

                            }

                        }

                }

                else if (p[to] == -1)

                {

                    p[to] = v;

                    if (mch[to] == -1)

                        return to;

                    to = mch[to];

                    selected[to] = true;

                    q[qt++] = to;

                }

            }

        }

        return -1;

    }

 

    public static int maxMatching(List<Integer>[] grph)

    {

        int n = grph.length;

        int[] mch = new int[n];

        Arrays.fill(mch, -1);

        int[] p = new int[n];

        for (int i = 0; i < n; ++i)

        {

            if (mch[i] == -1)

            {

                int v = findPath(grph, mch, p, i);

                while (v != -1)

                {

                    int pv = p[v];

                    int ppv = mch[pv];

                    mch[v] = pv;

                    mch[pv] = v;

                    v = ppv;

                }

            }

        }

        int matches = 0;

        for (int i = 0; i < n; ++i)

            if (mch[i] != -1)

                ++matches;

        return matches / 2;

    }

 

    @SuppressWarnings("unchecked")

    public static void main(String[] args)

    {

        Scanner scr = new Scanner(System.in);

        System.out.println("Enter How Many Vertices You Want: ");

        int vrt = scr.nextInt();

        System.out.println("Enter How Many Edges You Want: ");

        int edg = scr.nextInt();

        List<Integer>[] gr = new List[vrt];

        for (int d = 0; d < vrt; d++)

        {

            gr[d] = new ArrayList<Integer>();

        }

        System.out.println("Enter Edges FROM ant TO");

        for (int d = 0; d < edg; d++)

        {

            gr[scr.nextInt()].add(scr.nextInt());

        }

        System.out.println("Maximum matching for the given graph is: "

                + maxMatching(gr));

        scr.close();

    }

}